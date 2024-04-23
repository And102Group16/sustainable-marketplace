package com.example.sustainify

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
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
        val imageGallery = findViewById<LinearLayout>(R.id.imageGallery)
        val images = emptyList<String>() // TODO retrieve images from the object passed to this activity

        for (image in images){
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(128, 128)
            imageView.setPadding(8, 0, 8, 0)
            Glide.with(this)
                .asBitmap()
                .load(image)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val roundedDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
                        roundedDrawable.cornerRadius = resources.getDimension(R.dimen.corner_radius)
                        imageView.setImageDrawable(roundedDrawable)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
//                        TODO("Not yet implemented")
                    }
                })
            imageGallery.addView(imageView)
        }
    }
}
