package com.example.sustainify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

class ListingsFragment : Fragment() {

    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addProductButton: Button
    private lateinit var applyFiltersBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_listings, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewListings)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize Add Product Button
        addProductButton = view.findViewById(R.id.buttonAddProduct)

        applyFiltersBtn = view.findViewById(R.id.btnApplyFilters)

        // Example data (replace with your actual data)
        val items = mutableListOf(
            Item(
                "Wooden Chair",
                "A small wooden chair some 2 years old",
                10.0,
                listOf("image1.jpg"),
                "450 Lindbergh Place NE",
                "Mahima Sharma",
                "450 Lindbergh Place NE",
                ""
            ),
            Item(
                "Solid black desk",
                "A solid black standing desk",
                20.0,
                listOf("image2.jpg"),
                "50 Lindbergh Place NE",
                "Banu",
                "50 Lindbergh Place NE",
                ""
            )
            // Add more sample items as needed
        )

        applyFiltersBtn.setOnClickListener {
            // Create an Intent object
            val intent = Intent(context, FilterItemsFragment::class.java)

            // Add any extra data to the intent if needed
//            intent.putExtra("key", value)

            // Start the activity using the intent
            startActivity(intent)

//            changeFragment(FilterItemsFragment());
        }

        // Example role (replace with actual role)
        val role = "seller"

        // Show Add Product Button only for sellers
        if (role == "seller") {
            addProductButton.visibility = View.VISIBLE
            addProductButton.setOnClickListener {
                // Handle Add Product Button click
                val intent = Intent(requireActivity(), AddProduct::class.java)
                startActivityForResult(intent, ADD_PRODUCT_REQUEST_CODE)
            }
        } else {
            addProductButton.visibility = View.GONE
        }

        // Initialize and set up the adapter
        itemsAdapter = ItemsAdapter(items, role, requireActivity())
        recyclerView.adapter = itemsAdapter

        return view
    }

    fun changeFragment(newFragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

        // Replace the current fragment with the new one
        fragmentTransaction.replace(R.id.viewPager, newFragment)

        // Optionally, you can add the transaction to the back stack
        // fragmentTransaction.addToBackStack(null)

        // Commit the transaction
        fragmentTransaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("Hello hello","mahima")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PRODUCT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Retrieve data from intent
            val heading = data?.getStringExtra("itemName")
            val price = data?.getDoubleExtra("setPrice", 0.0)
            val pickupLocation = data?.getStringExtra("pickUpLocation")
            val contactInfo = data?.getStringExtra("contactInfo")

            // Create new item and add it to the list
            val newItem = Item(heading ?: "", "", price ?: 0.0, listOf(), pickupLocation ?: "", "", "", contactInfo ?: "")
            itemsAdapter.addItem(newItem) // You need to implement this method in your ItemsAdapter
        }
    }

    companion object {
        private const val ADD_PRODUCT_REQUEST_CODE = 1001
    }
}


