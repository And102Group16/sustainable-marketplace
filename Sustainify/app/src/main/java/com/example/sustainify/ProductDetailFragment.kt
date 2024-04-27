package com.example.sustainify

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

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

        val item: Item? = intent.getSerializableExtra("item") as? Item
        item?.let {
            findViewById<TextView>(R.id.itemHeading).text = it.heading
            findViewById<TextView>(R.id.priceTextView).text = "$${it.price}"
            findViewById<TextView>(R.id.descriptionTextView).text = it.description
            findViewById<TextView>(R.id.pickupLocationTextView).text = it.pickupLocation
            findViewById<TextView>(R.id.sellerNameTextView).text = it.seller

            val imageGallery = findViewById<LinearLayout>(R.id.imageGallery)
            Log.d("Image display into gallery - outside", it.images.toString())
            for (image in it.images){
                Log.d("Image display into gallery - inside", it.images.toString())
                val imageView = ImageView(this)
                imageView.layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    400
                )
                imageView.setPadding(8, 0, 8, 0)
                Glide.with(this)
                    .load(image)
                    .into(imageView)
                imageGallery.addView(imageView)
            }
        }
    }
}
