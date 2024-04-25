package com.example.sustainify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddProduct : AppCompatActivity() {
    private lateinit var itemNameEditText: EditText
    private lateinit var setPriceEditText: EditText
    private lateinit var pickUpLocationButton: Button
    private lateinit var contactInfoEditText: EditText
    private lateinit var addImageButton: ImageView
    private lateinit var submitButton: Button
    private lateinit var backButton: ImageView
    private lateinit var horizontalScroll: HorizontalScrollView
    private var images: ArrayList<Uri> = ArrayList()

    private val REQUEST_CODE = 100
    private var pickupLatLng = ""
    private var pickupAddress = ""



    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)) { uris ->
            // Callback is invoked after the user selects media items or closes the photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
//                images.clear()
                images.addAll(uris)
                displayIntoGallery(images)
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
        pickUpLocationButton = findViewById(R.id.pickUp)
        contactInfoEditText = findViewById(R.id.contactInfo)
        addImageButton = findViewById(R.id.addImg)
        submitButton = findViewById(R.id.button)
        backButton = findViewById(R.id.backButton)
        horizontalScroll = findViewById(R.id.horizontalScroll)
        horizontalScroll.visibility = View.GONE

        addImageButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val imagesTemp = pickImages()
                Log.d("Image picker ----- just got invoked", images.toString())
                displayIntoGallery(imagesTemp)
            }
        }


        backButton.setOnClickListener {
            finish()
        }

        submitButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val setPrice = setPriceEditText.text.toString()
//            val pickUpLocation = pickUpLocationButton.text.toString()
            val pickUpLocation = pickupLatLng
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

        val launchMapButton = findViewById<Button>(R.id.pickUp)
        launchMapButton.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private fun pickImages(): ArrayList<Uri> {
        pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        return images
    }

    private fun displayIntoGallery(imagesTemp: ArrayList<Uri>) {
        if (imagesTemp.isNotEmpty()) {
            horizontalScroll.visibility = View.VISIBLE
        }
        val imageGallery = findViewById<LinearLayout>(R.id.imageGallery)
        Log.d("Image display into gallery - outside", images.toString())
        for (image in imagesTemp){
            Log.d("Image display into gallery - inside", images.toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    // Extract any data passed back from the activity
                    pickupLatLng = data?.getStringExtra("pickupLatLng")?:pickupLatLng
                    pickupAddress = data?.getStringExtra("pickupAddress")?:pickupLatLng
                    Log.d("Location received back", pickupLatLng)
                    Log.d("Location received back", pickupAddress)

                    Toast.makeText(this, "$pickupLatLng", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "$pickupAddress", Toast.LENGTH_SHORT).show()
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Failed to receive location", Toast.LENGTH_SHORT).show()
            }
        }
        pickupLatLng = data?.getStringExtra("pickupLatLng")?:pickupLatLng
        Log.d("Location received back", pickupLatLng)
    }

}