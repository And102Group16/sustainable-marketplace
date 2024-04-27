package com.example.sustainify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        // Retrieve loginType from intent
        val loginType = intent.getStringExtra("loginType")

        // Pass loginType to ListingsFragment
        val listingsFragment = ListingsFragment().apply {
            arguments = Bundle().apply {
                putString("loginType", loginType)
            }
        }

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(listingsFragment, "Listings")
        adapter.addFragment(ProfileFragment(), "Profile")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
