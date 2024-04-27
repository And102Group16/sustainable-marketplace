package com.example.sustainify

data class ProfileData(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val cardNumber: String = "",
    val cardCVC: String = "",
    val billingAddress: String = "",
    val newPassword: String = "",
    val confirmPassword: String = ""
)