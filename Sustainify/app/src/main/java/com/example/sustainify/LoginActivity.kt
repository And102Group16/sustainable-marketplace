package com.example.sustainify

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var auth: FirebaseAuth

    private lateinit var signupText: TextView
    private lateinit var rgLoginType : RadioGroup
    private var selectedOption: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.btnLogin)
        edtEmail = findViewById(R.id.etEmail)
        edtPassword = findViewById(R.id.etPassword)
        auth = FirebaseAuth.getInstance()

        signupText = findViewById(R.id.tvSignUp)
        rgLoginType = findViewById(R.id.rgLoginType)

        // Load saved login type
        val lastUsedEmail = SharedPreferencesManager.getLastUsedEmail(this)
        if (lastUsedEmail != null) {
            val userData = SharedPreferencesManager.getUserData(this, lastUsedEmail)
            val loginType = userData["LoginType"]
            when (loginType) {
                "buyer" -> rgLoginType.check(R.id.rbBuyer)
                "seller" -> rgLoginType.check(R.id.rbSeller)
            }
        }

        // Login type selection
        rgLoginType.setOnCheckedChangeListener { _, checkedId ->
            selectedOption = when (checkedId) {
                R.id.rbBuyer -> "buyer"
                R.id.rbSeller -> "seller"
                else -> ""
            }
        }

        // Login button click listener
        loginButton.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                if (password.isEmpty()) Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userData = SharedPreferencesManager.getUserData(this, email)
                    val userName = userData["UserName"] ?: "User"
                    val loginType = userData["LoginType"]

                    when (loginType) {
                        "buyer" -> rgLoginType.check(R.id.rbBuyer)
                        "seller" -> rgLoginType.check(R.id.rbSeller)
                    }

                    Toast.makeText(this, "Welcome back $userName", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                } else {
                    val errorCode = (task.exception as FirebaseAuthException).errorCode
                    handleError(errorCode)
                }
            }
        }

        // Navigate to SignUp Activity
        signupText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun handleError(errorCode: String) {
        val message = when (errorCode) {
            "ERROR_INVALID_EMAIL" -> "The email address is badly formatted."
            "ERROR_WRONG_PASSWORD" -> "The password is incorrect."
            "ERROR_USER_NOT_FOUND" -> "There is no user corresponding to the email address."
            "ERROR_USER_DISABLED" -> "The user account has been disabled by an administrator."
            "ERROR_TOO_MANY_REQUESTS" -> "Too many attempts to sign in as this user."
            else -> "Authentication failed."
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.w(TAG, "signInWithEmail:failure $errorCode")
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("loginType", selectedOption)
        startActivity(intent)
        finish()
    }
}