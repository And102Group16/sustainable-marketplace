package com.example.sustainify

import android.content.Context
import android.content.SharedPreferences
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object SharedPreferencesManager {
    private const val PREF_NAME = "AppPreferences"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserData(context: Context, name: String, email: String, phone: String, cardNumber: String, cardCVC: String, billingAddress: String, presentAddress: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString("UserName", name)
        editor.putString("UserEmail", email)
        editor.putString("UserPhone", phone)
        editor.putString("CardNumber", cardNumber)
        editor.putString("CardCVC", cardCVC)
        editor.putString("BillingAddress", billingAddress)
        editor.putString("PresentAddress", presentAddress)
        editor.apply()
    }

    fun getUserData(context: Context): HashMap<String, String> {
        val preferences = getSharedPreferences(context)
        val name = preferences.getString("UserName", "Default Name") ?: "Default Name"
        val email = preferences.getString("UserEmail", "Default Email") ?: "Default Email"
        val phone = preferences.getString("UserPhone", "Default Phone") ?: "Default Phone"
        val cardNumber = preferences.getString("CardNumber", "Default CardNumber") ?: "Default CardNumber"
        val cardCVC = preferences.getString("CardCVC", "Default CardCVC") ?: "Default CardCVC"
        val billingAddress = preferences.getString("BillingAddress", "Default BillingAddress") ?: "Default BillingAddress"
        val presentAddress = preferences.getString("PresentAddress", "Default PresentAddress") ?: "Default PresentAddress"

        return hashMapOf(
            "UserName" to name,
            "UserEmail" to email,
            "UserPhone" to phone,
            "CardNumber" to cardNumber,
            "CardCVC" to cardCVC,
            "BillingAddress" to billingAddress,
            "PresentAddress" to presentAddress
        )
    }

    fun writeUserDataToFile(context: Context) {
        // First, fetch all the user data from SharedPreferences
        val userData = getUserData(context)

        // Create the file in internal storage
        val fileName = "UserData.txt"
        val file = File(context.filesDir, fileName)

        try {
            FileOutputStream(file).use { fos ->
                userData.forEach { (key, value) ->
                    fos.write("$key: $value\n".toByteArray())
                }
                fos.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}