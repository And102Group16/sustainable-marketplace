package com.example.sustainify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtCardNumber: EditText
    private lateinit var edtCardCVC: EditText
    private lateinit var edtBillingAddress: EditText
    private lateinit var edtPresentAddress: EditText
    private lateinit var toggleAddress: SwitchCompat

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize fields
        btnSignup = findViewById(R.id.btnSignup)
        edtEmail = findViewById(R.id.etEmail)
        edtPassword = findViewById(R.id.etPassword)
        edtConfirmPassword = findViewById(R.id.etConfirmPassword)
        edtName = findViewById(R.id.etName)
        edtPhone = findViewById(R.id.etPhone)
        edtCardNumber = findViewById(R.id.etCardNumber)
        edtCardCVC = findViewById(R.id.etCardCVC)
        edtBillingAddress = findViewById(R.id.etBillingAddress)
        edtPresentAddress = findViewById(R.id.etPresentAddress)
        toggleAddress = findViewById(R.id.switchBillingAddress)
        auth = FirebaseAuth.getInstance()

        // Populate physical address with billing address info if toggle is clicked
        toggleAddress.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                edtPresentAddress.setText(edtBillingAddress.text.toString())
            }
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            // Navigate back to the LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Finish this activity to prevent returning here
        }

        btnSignup.setOnClickListener {
            // Collect user input from EditText fields
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val confirmPassword = edtConfirmPassword.text.toString().trim()
            val name = edtName.text.toString().trim()
            val phone = edtPhone.text.toString().trim()
            val cardNumber = edtCardNumber.text.toString().trim()
            val cardCVC = edtCardCVC.text.toString().trim()
            val billingAddress = edtBillingAddress.text.toString().trim()
            val presentAddress = edtPresentAddress.text.toString().trim()

            // Check if any field is empty
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() ||
                phone.isEmpty() || cardNumber.isEmpty() || cardCVC.isEmpty() || billingAddress.isEmpty() ||
                (presentAddress.isEmpty() && !toggleAddress.isChecked)
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if passwords match
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Authenticate and register the user
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User is successfully registered
                        val user = FirebaseAuth.getInstance().currentUser
                        val userId = user!!.uid

                        // Save additional user information in Firebase Database
                        val userInfo = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "phone" to phone,
                            "cardNumber" to cardNumber,
                            "cardCVC" to cardCVC,
                            "billingAddress" to billingAddress,
                            "presentAddress" to if (toggleAddress.isChecked) billingAddress else presentAddress
                        )
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(userId).setValue(userInfo)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Registration successful, please log in.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Redirect to LoginActivity for user to log in with their new account
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish() // Finish this activity so the user can't go back
                                } else {
                                    // Handle failures in saving data
                                    Toast.makeText(
                                        this,
                                        "Failed to save user data",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // Handle registration failure
                        Toast.makeText(
                            this,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}