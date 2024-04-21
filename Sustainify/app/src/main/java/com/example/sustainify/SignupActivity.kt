package com.example.sustainify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnSignup = findViewById(R.id.btnSignup)

            btnSignup.setOnClickListener {
            // Handle Add Product Button click
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}