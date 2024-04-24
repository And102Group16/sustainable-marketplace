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

        btnSignup.setOnClickListener {
            btnSignup.isEnabled = false  // Disable the button to prevent multiple submissions

            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val confirmPassword = edtConfirmPassword.text.toString().trim()
            val name = edtName.text.toString().trim()
            val phone = edtPhone.text.toString().trim()
            val cardNumber = edtCardNumber.text.toString().trim()
            val cardCVC = edtCardCVC.text.toString().trim()
            val billingAddress = edtBillingAddress.text.toString().trim()
            val presentAddress = edtPresentAddress.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() ||
                phone.isEmpty() || cardNumber.isEmpty() || cardCVC.isEmpty() || billingAddress.isEmpty() ||
                (presentAddress.isEmpty() && !toggleAddress.isChecked)
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                btnSignup.isEnabled = true  // Re-enable the button
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                btnSignup.isEnabled = true  // Re-enable the button
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Save user data locally
                    SharedPreferencesManager.saveUserData(this, name, email, phone, cardNumber, cardCVC, billingAddress, presentAddress)
                    // Write data to the file immediately after saving to SharedPreferences
                    // SharedPreferencesManager.writeUserDataToFile(this)

                    val user = auth.currentUser
                    val db = FirebaseDatabase.getInstance().reference
                    val userId = user?.uid
                    val userInfo = hashMapOf(
                        "name" to name,
                        "phone" to phone,
                        "cardNumber" to cardNumber,
                        "cardCVC" to cardCVC,
                        "billingAddress" to billingAddress,
                        "presentAddress" to presentAddress
                    )

                    userId?.let {
                        db.child("Users").child(it).setValue(userInfo).addOnSuccessListener {
                            Toast.makeText(this@SignupActivity, "User registered and data saved!", Toast.LENGTH_SHORT).show()
                            SharedPreferencesManager.writeUserDataToFile(this)

                            val intent = Intent(this@SignupActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this@SignupActivity, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                            btnSignup.isEnabled = true
                        }
                    }
                } else {
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    btnSignup.isEnabled = true
                }
            }
        }
    }
}