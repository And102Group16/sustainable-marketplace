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

    fun saveUserData(
        context: Context,
        name: String,
        email: String,
        phone: String,
        cardNumber: String,
        cardCVC: String,
        billingAddress: String,
        presentAddress: String
    ) {
        val editor = getSharedPreferences(context).edit()
        val userPrefix = "user_$email"
        editor.putString("${userPrefix}_UserName", name)
        editor.putString("${userPrefix}_UserEmail", email)
        editor.putString("${userPrefix}_UserPhone", phone)
        editor.putString("${userPrefix}_CardNumber", cardNumber)
        editor.putString("${userPrefix}_CardCVC", cardCVC)
        editor.putString("${userPrefix}_BillingAddress", billingAddress)
        editor.putString("${userPrefix}_PresentAddress", presentAddress)
        editor.apply()
    }

    fun getUserData(context: Context, email: String): HashMap<String, String> {
        val preferences = getSharedPreferences(context)
        val userPrefix = "user_$email"
        val userData = HashMap<String, String>()
        userData["UserName"] =
            preferences.getString("${userPrefix}_UserName", "Default Name") ?: "Default Name"
        userData["UserEmail"] = preferences.getString("${userPrefix}_UserEmail", email) ?: email
        userData["UserPhone"] =
            preferences.getString("${userPrefix}_UserPhone", "Default Phone") ?: "Default Phone"
        userData["CardNumber"] =
            preferences.getString("${userPrefix}_CardNumber", "Default CardNumber")
                ?: "Default CardNumber"
        userData["CardCVC"] =
            preferences.getString("${userPrefix}_CardCVC", "Default CardCVC") ?: "Default CardCVC"
        userData["BillingAddress"] =
            preferences.getString("${userPrefix}_BillingAddress", "Default BillingAddress")
                ?: "Default BillingAddress"
        userData["PresentAddress"] =
            preferences.getString("${userPrefix}_PresentAddress", "Default PresentAddress")
                ?: "Default Present Address"
        return userData
    }


    fun writeUserDataToFile(context: Context, email: String) {
        val userData = getUserData(context, email)
        val fileName = "UserData.txt"
        val file = File(context.filesDir, fileName)
        try {
            FileOutputStream(file).use { fos ->
                userData.forEach { (key, value) ->
                    fos.write("$key: $value\n".toByteArray())
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getLastUsedEmail(context: Context): String? {
        return getSharedPreferences(context).getString("LastUsedEmail", null)
    }
}