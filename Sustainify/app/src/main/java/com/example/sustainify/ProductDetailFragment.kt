package com.example.sustainify

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProductDetailFragment : AppCompatActivity() {

    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_product_detail) // Change to your activity layout

        backButton = findViewById(R.id.backButtonPd)

        backButton.setOnClickListener {
            // Close the current activity and return to the previous one
            finish()
        }
    }
}
