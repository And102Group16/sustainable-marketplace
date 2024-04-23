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
import android.util.Patterns


class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var auth: FirebaseAuth

    private lateinit var signupText: TextView
    private lateinit var rgLoginType : RadioGroup
    var selectedOption: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.btnLogin)
        val edtEmail = findViewById<EditText>(R.id.etEmail)
        val edtPassword = findViewById<EditText>(R.id.etPassword)
        val signupText = findViewById<TextView>(R.id.tvSignUp)
        val rgLoginType = findViewById<RadioGroup>(R.id.rgLoginType)
        auth = FirebaseAuth.getInstance()

        rgLoginType?.setOnCheckedChangeListener { group, checkedId ->
            selectedOption = when (checkedId) {
                R.id.rbBuyer -> "buyer"
                R.id.rbSeller -> "seller"
                else -> "" // Handle default case if needed
            }
        }

        loginButton?.setOnClickListener {
            val email = edtEmail?.text.toString().trim()
            val password = edtPassword?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                }
                if (password.isEmpty()) {
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Authenticate the user
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Authentication success
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            navigateToHome()
                        } else {
                            // Authentication fail
                            task.exception?.let {
                                if (it is FirebaseAuthException) {
                                    handleError(it.errorCode)
                                } else {
                                    Toast.makeText(this, "Authentication failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
            }
        }

        signupText?.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
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