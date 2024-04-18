package com.example.sustainify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListingsFragment : Fragment() {

    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addProductButton: Button

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

        // Example data (replace with your actual data)
        val items = listOf(
            Item(
                "Wooden Chair",
                "A small wooden chair some 2 years old",
                10.0,
                listOf("image1.jpg"),
                "450 Lindbergh Place NE",
                "Mahima Sharma",
                "450 Lindbergh Place NE"
            ),
            Item(
                "Solid black desk",
                "A solid black standing desk",
                20.0,
                listOf("image2.jpg"),
                "50 Lindbergh Place NE",
                "Banu",
                "50 Lindbergh Place NE"
            ),
            // Add more sample items as needed
        )

        // Example role (replace with actual role)
        val role = "seller"

        // Show Add Product Button only for sellers
        if (role == "seller") {
            addProductButton.visibility = View.VISIBLE
            addProductButton.setOnClickListener {
                // Handle Add Product Button click
                val intent = Intent(requireActivity(), AddProduct::class.java)
                startActivity(intent)
            }
        } else {
            addProductButton.visibility = View.GONE
        }

        // Initialize and set up the adapter
        itemsAdapter = ItemsAdapter(items, role)
        recyclerView.adapter = itemsAdapter

        return view
    }
}


