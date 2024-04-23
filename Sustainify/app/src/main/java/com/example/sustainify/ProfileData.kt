package com.example.sustainify

data class ProfileData(
    val name: String = "",
    val email: String = "",
    val phone: Long = 0L,
    val cardNum: Long = 0L,
    val cvc: Int = 0,
    val billingAdd: String = "",
    val newPass: String = "",
    val confirmPass: String = ""
)