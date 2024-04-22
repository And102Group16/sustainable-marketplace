package com.example.sustainify

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import java.lang.Exception

class AddProduct : AppCompatActivity() {
    private lateinit var itemNameEditText: EditText
    private lateinit var setPriceEditText: EditText
    private lateinit var pickUpLocationEditText: EditText
    private lateinit var contactInfoEditText: EditText
    private lateinit var addImageButton: ImageView
    private lateinit var submitButton: Button
    private lateinit var backButton: ImageView

    private var images: ArrayList<Uri> = ArrayList()


    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)) { uris ->
            // Callback is invoked after the user selects media items or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                images.clear()
                images.addAll(uris)
                Log.d("Image picker picked -->", images.toString())
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product)


        itemNameEditText = findViewById(R.id.itemName)
        setPriceEditText = findViewById(R.id.setprice)
        pickUpLocationEditText = findViewById(R.id.pickUp)
        contactInfoEditText = findViewById(R.id.contactInfo)
        addImageButton = findViewById(R.id.addImg)
        submitButton = findViewById(R.id.button)
        backButton = findViewById(R.id.backButton)

        addImageButton.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val imagesTemp = pickImages()
                Log.d("Image picker ----- just got invoked", images.toString())
                // TODO after the images are picked, display them in a horizontal scroll view
                displayIntoGallery(imagesTemp)
            }
        }


        backButton.setOnClickListener {
            finish()
        }

        submitButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val setPrice = setPriceEditText.text.toString()
            val pickUpLocation = pickUpLocationEditText.text.toString()
            val contactInfo = contactInfoEditText.text.toString()

            var imageUrlList: MutableList<String>

            CoroutineScope(Dispatchers.IO).launch {
                submitButton.text = "Uploading..."
                imageUrlList = addImageToFirebaseStorage(images)
                Log.d("Image upload links", imageUrlList.toString())

                val intent = Intent().apply {
                    putExtra("itemName", itemName)
                    putExtra("setPrice", setPrice)
                    putExtra("pickUpLocation", pickUpLocation)
                    putExtra("contactInfo", contactInfo)
                    // TODO split the urls by comma
                    putExtra("imageUrls", imageUrlList.toString())
                }
                setResult(Activity.RESULT_OK, intent)
                images.clear()
                finish()
            }
        }

    }

    private fun pickImages(): ArrayList<Uri> {
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        return images
    }

    private fun displayIntoGallery(imagesTemp: ArrayList<Uri>) {
        val imageGallery = findViewById<LinearLayout>(R.id.imageGallery)
        Log.d("Image display into gallery - outside", images.toString())
        for (image in imagesTemp){
            Log.d("Image display into gallery - inside", images.toString())
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(128, 128)
            imageView.setPadding(8, 0, 8, 0)
            Glide.with(this)
                .load(image)
                .into(imageView)
            imageGallery.addView(imageView)
        }
    }

    private suspend fun addImageToFirebaseStorage(localUris: ArrayList<Uri>): ArrayList<String> {
        val result = ArrayList<String>()
        val timeStamp = System.currentTimeMillis().toString()
        val storageRef = FirebaseStorage.getInstance().getReference("images").child(timeStamp)

        localUris.forEach{localUri ->
            try {
                val downloadUrl = storageRef
                    .putFile(localUri).await()
                    .storage.downloadUrl.await()
                downloadUrl?.let {
                    result.add(downloadUrl.toString())
                }
            } catch (e: Exception){
                Log.d("Image Upload Failed", localUri.toString())
            }
        }

        return result
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Image successfully picked, handle the result here
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    // Add the image URI to the list
                    Log.d("Picked image", it.toString())
                    images.add(it)
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