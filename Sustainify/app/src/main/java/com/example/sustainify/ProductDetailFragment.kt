package com.example.sustainify

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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

        val item: Item? = intent.getSerializableExtra("item") as? Item
        item?.let {
            findViewById<TextView>(R.id.itemHeading).text = it.heading
            findViewById<TextView>(R.id.priceTextView).text = "$${it.price}"
            findViewById<TextView>(R.id.descriptionTextView).text = it.description
            findViewById<TextView>(R.id.pickupLocationTextView).text = it.pickupLocation
            findViewById<TextView>(R.id.sellerNameTextView).text = it.seller

            val imageGallery = findViewById<LinearLayout>(R.id.imageGallery)
            it.images.forEach { imageUrl ->
                val imageView = ImageView(this)
                imageView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                imageView.setPadding(8, 0, 8, 0)
                Glide.with(this)
                    .load(imageUrl)
                    .into(imageView)
                imageGallery.addView(imageView)
            }
        }

        val imageGallery = findViewById<LinearLayout>(R.id.imageGallery)
//        val images = emptyList<String>() // TODO retrieve images from the object passed to this activity
//
//        for (image in images){
//            val imageView = ImageView(this)
//            imageView.layoutParams = LinearLayout.LayoutParams(128, 128)
//            imageView.setPadding(8, 0, 8, 0)
//            Glide.with(this)
//                .asBitmap()
//                .load(image)
//                .into(object : CustomTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        val roundedDrawable = RoundedBitmapDrawableFactory.create(resources, resource)
//                        roundedDrawable.cornerRadius = resources.getDimension(R.dimen.corner_radius)
//                        imageView.setImageDrawable(roundedDrawable)
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {
////                        TODO("Not yet implemented")
//                    }
//                })
//            imageGallery.addView(imageView)
//        }

//        val imageGallery = findViewById<LinearLayout>(R.id.imageGallery)
//        item?.images?.forEach { imageUrl ->
//            val imageView = ImageView(this)
//            imageView.layoutParams = LinearLayout.LayoutParams(128, 128)
//            imageView.setPadding(8, 0, 8, 0)
//            Glide.with(this)
//                .load(imageUrl)
//                .into(imageView)
//            imageGallery.addView(imageView)
//        }
    }
}
