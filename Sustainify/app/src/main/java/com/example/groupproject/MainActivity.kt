package com.example.groupproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var itemNameEditText: EditText
    private lateinit var setPriceEditText: EditText
    private lateinit var pickUpLocationEditText: EditText
    private lateinit var contactInfoEditText: EditText
    private lateinit var addImageButton: ImageView
    private lateinit var submitButton: Button

    private var images: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemNameEditText = findViewById(R.id.itemName)
        setPriceEditText = findViewById(R.id.setprice)
        pickUpLocationEditText = findViewById(R.id.pickUp)
        contactInfoEditText = findViewById(R.id.contactInfo)
        addImageButton = findViewById(R.id.addImg)
        submitButton = findViewById(R.id.button)

        addImageButton.setOnClickListener {
            openImagePicker()
        }

        submitButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val setPrice = setPriceEditText.text.toString()
            val pickUpLocation = pickUpLocationEditText.text.toString()
            val contactInfo = contactInfoEditText.text.toString()

            // You can perform actions here like sending data to a server or storing it locally

            // Clear EditText fields after submission
            itemNameEditText.text.clear()
            setPriceEditText.text.clear()
            pickUpLocationEditText.text.clear()
            contactInfoEditText.text.clear()

            // Clear images array after submission
            images.clear()
        }

    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Image successfully picked, handle the result here
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    // Add the image URI to the list
                    images.add(it.toString())
                }
            }
        }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        imagePickerLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }
}