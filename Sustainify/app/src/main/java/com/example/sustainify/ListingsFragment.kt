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
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ListingsFragment : Fragment() {

    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addProductButton: Button
    private lateinit var applyFiltersBtn : Button
    private lateinit var roleButton : Button

    private val database = Firebase.database
    private val productRef = database.getReference("product")
    val databaseReference = FirebaseDatabase.getInstance().getReference("product")

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
        roleButton = view.findViewById(R.id.btnRoleBuyer)

        // Example data (replace with your actual data)
        val items = mutableListOf<Item>()

        // Add a listener to fetch data from Firebase
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing items
                items.clear()

                // Iterate through the dataSnapshot and populate the items list
                for (dataSnapshot in snapshot.children) {
                    val item = dataSnapshot.getValue(Item::class.java)
                    item?.let {
                        items.add(it)
                    }
                }

                // Notify the adapter that the data set has changed
                itemsAdapter.notifyItemRangeInserted(0,snapshot.childrenCount.toInt())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })

        applyFiltersBtn.setOnClickListener {
            // Create an Intent object
            val intent = Intent(context, FilterItemsActivity::class.java)

            // Add any extra data to the intent if needed
//            intent.putExtra("key", value)

            // Start the activity using the intent
            startActivity(intent)

//            changeFragment(FilterItemsFragment());
        }

        // Example role (replace with actual role)
        val role = arguments?.getString("loginType") ?: "seller"
        roleButton.text = "Role: " + role

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
            val description = data?.getStringExtra("itemDescription")
            val price = (data?.getStringExtra("setPrice")?:"0.0")
            val pickupLocation = data?.getStringExtra("pickUpLocation")
            val contactInfo = data?.getStringExtra("contactInfo")

            // Create new item and add it to the list
            val newItem = Item(heading ?: "", description ?: "", price, ArrayList<String>() , pickupLocation ?: "", "")
            itemsAdapter.addItem(newItem) // You need to implement this method in your ItemsAdapter
        }
        else if (requestCode == PRICE_FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Retrieve data from intent
            val maxPrice = data?.getStringExtra("maxPrice")


            // Create new item and add it to the list
//            val newItem = Item(heading ?: "", description ?: "", price, ArrayList<String>() , pickupLocation ?: "", "")
//            itemsAdapter.addItem(newItem) // You need to implement this method in your ItemsAdapter
        }

    }

    companion object {
        private const val ADD_PRODUCT_REQUEST_CODE = 1001
        private const val PRICE_FILTER_REQUEST_CODE = 1002
    }
}


