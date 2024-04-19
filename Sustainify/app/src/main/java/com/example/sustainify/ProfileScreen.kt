package com.example.sustainify

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.sustainify.R


class ProfileScreen : AppCompatActivity() {

    private lateinit var addName: EditText
    private lateinit var emailId: EditText
    private lateinit var phoneNum: EditText
    private lateinit var cardNum: EditText
    private lateinit var cvc: EditText
    private lateinit var billingAdd: EditText
    private lateinit var newPassword: EditText
    private lateinit var updateProfileButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        addName = findViewById(R.id.addName)
        emailId = findViewById(R.id.emailId)
        phoneNum = findViewById(R.id.phoneNum)
        cardNum = findViewById(R.id.cardNum)
        cvc = findViewById(R.id.cvc)
        billingAdd = findViewById(R.id.billingAdd)
        newPassword = findViewById(R.id.newPassword)
        updateProfileButton = findViewById(R.id.button3)

        updateProfileButton.setOnClickListener {
            // Handle update profile button click
            val name = addName.text.toString()
            val email = emailId.text.toString()
            val phone = phoneNum.text.toString()
            val cardNumber = cardNum.text.toString()
            val cvc = cvc.text.toString()
            val billingAddress = billingAdd.text.toString()
            val newPassword = newPassword.text.toString()

            // You can perform actions with the data, such as updating the user's profile
        }
    }
}

