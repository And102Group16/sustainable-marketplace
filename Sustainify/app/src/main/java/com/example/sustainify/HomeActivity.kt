package com.example.sustainify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListingsFragment(), "Listings")
        adapter.addFragment(ProfileFragment(), "Profile")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
