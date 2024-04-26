package com.example.sustainify
import java.io.Serializable
data class Item(
    val heading: String = "",
    val description: String = "",
    val price: String = "",
    val images: ArrayList<String> = ArrayList(),
    val pickupLocation: String = "",
    val seller: String = ""
   // val sellerAddress: String,
    // val contactInfo: String
) : Serializable
