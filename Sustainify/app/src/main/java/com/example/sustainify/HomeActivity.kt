package com.example.sustainify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            logout()  // Call the simplified logout method
        }

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListingsFragment(), "Listings")
        adapter.addFragment(ProfileFragment(), "Profile")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)


    }

    private fun logout() {
        // Sign out the current user
        FirebaseAuth.getInstance().signOut()
        // Show a toast message to inform the user
        Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show()
        // Redirect to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        // Finish HomeActivity to prevent returning to it on pressing back button
        finish()
    }
}
