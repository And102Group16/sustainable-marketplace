package com.example.sustainify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

class ProfileFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var cardNumberEditText: EditText
    private lateinit var cardCVCEditText: EditText
    private lateinit var billingAddressEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize EditTexts
        nameEditText = view.findViewById(R.id.addName)
        emailEditText = view.findViewById(R.id.emailId)
        phoneEditText = view.findViewById(R.id.phoneNum)
        cardNumberEditText = view.findViewById(R.id.cardNum)
        cardCVCEditText = view.findViewById(R.id.cvc)
        billingAddressEditText = view.findViewById(R.id.billingAdd)
        passwordEditText = view.findViewById(R.id.newPassword)
        confirmPasswordEditText = view.findViewById(R.id.confirmNewPassword)



        // Button
        val updateButton: Button = view.findViewById(R.id.button3)
        updateButton.setOnClickListener {
            updateProfile()
        }

        // Load the user data
        loadData()
    }

    private fun loadData() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Take email directly from the FirebaseAuth instance
            emailEditText.setText(user.email)
            // Initialize Firebase Database
            database = FirebaseDatabase.getInstance().reference

            // Firebase Database path
            val userRef = database.child("Users").child(user.uid)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.getValue(ProfileData::class.java)
                        // Populate the EditText fields
                        nameEditText.setText(userData?.name)
                        phoneEditText.setText(userData?.phone)
                        cardNumberEditText.setText(userData?.cardNumber)
                        cardCVCEditText.setText(userData?.cardCVC)
                        billingAddressEditText.setText(userData?.billingAddress)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors
                    Log.e("Database", "Error loading data", databaseError.toException())
                }
            })
        } else {
            Log.e("ProfileFragment", "No authenticated user found")
        }
    }

    private fun updateProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Retrieve user input
            val newName = nameEditText.text.toString().trim()
            val newPhone = phoneEditText.text.toString().trim()
            val newCardNumber = cardNumberEditText.text.toString().trim()
            val newCardCVC = cardCVCEditText.text.toString().trim()
            val newBillingAddress = billingAddressEditText.text.toString().trim()
            val newPassword = passwordEditText.text.toString().trim()
            val newConfirmPassword = confirmPasswordEditText.text.toString().trim()

            // Check if the new passwords match and are not empty
            if (newPassword.isNotEmpty() && newPassword == newConfirmPassword) {
                // Update password in Firebase Authentication
                user.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("ProfileFragment", "Password updated successfully.")
                    } else {
                        Log.e("ProfileFragment", "Failed to update password.", task.exception)
                    }
                }
            } else {
                Toast.makeText(context, "Passwords do not match or are empty.", Toast.LENGTH_SHORT).show()
            }

            // Prepare data to update other user data
            val updatedUserData = mapOf(
                "name" to newName,
                "phone" to newPhone,
                "cardNumber" to newCardNumber,
                "cardCVC" to newCardCVC,
                "billingAddress" to newBillingAddress
            )

            // Firebase Database path
            val userRef = database.child("Users").child(user.uid)

            // Update other data in Firebase Database
            userRef.updateChildren(updatedUserData)
                .addOnSuccessListener {
                    Toast.makeText(context, "User profile updated successfully.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to update user profile.", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "No authenticated user found", Toast.LENGTH_SHORT).show()
        }
    }

}