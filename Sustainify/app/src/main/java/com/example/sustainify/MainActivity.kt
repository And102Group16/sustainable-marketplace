package com.example.sustainify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 100 // 3000 // 3 seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }, splashTimeOut)
    }
    private fun firebaseWriteTest(){
        val db = Firebase.database
        val ref = db.getReference("listings")
        ref.setValue("Testing listing entry")
    }
}