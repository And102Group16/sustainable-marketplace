package com.example.sustainify

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var signupText: TextView
    private lateinit var rgLoginType : RadioGroup
    var selectedOption: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.btnLogin)
        signupText = findViewById(R.id.tvSignUp)
        rgLoginType = findViewById(R.id.rgLoginType)

        rgLoginType.setOnCheckedChangeListener { group, checkedId ->
            selectedOption = when (checkedId) {
                R.id.rbBuyer -> "buyer"
                R.id.rbSeller -> "seller"
                else -> "" // Handle default case if needed
            }
        }

        loginButton.setOnClickListener {
            // Handle Add Product Button click

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("loginType", selectedOption)
            startActivity(intent)
        }

        signupText.setOnClickListener {
            // Handle Add Product Button click
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}