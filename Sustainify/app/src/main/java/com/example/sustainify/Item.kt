package com.example.sustainify

data class Item(
    val heading: String,
    val description: String,
    val price: Double,
    val images: List<String>,
    val pickupLocation: String,
    val seller: String,
    val sellerAddress: String,
    val contactInfo: String
)
