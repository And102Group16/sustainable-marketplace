package com.example.sustainify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

class FilterItemsActivity : AppCompatActivity() {

    private lateinit var priceRangeSlider: RangeSlider
    private lateinit var radiusSlider: Slider
    private var priceLowerBound = "10"
    private var priceUpperBound = "500"
    private var searchRadius = "50"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_items)

        val applyFiltersButton = findViewById<Button>(R.id.applyFiltersButton)
        applyFiltersButton.setOnClickListener{
            val intent = Intent()
            intent.putExtra("priceLowerBound", priceLowerBound)
            intent.putExtra("priceUpperBound", priceUpperBound)
            intent.putExtra("searchRadius", searchRadius)

            setResult(RESULT_OK, intent)
            finish()
        }

        priceRangeSlider = findViewById(R.id.priceRangeSlider)
        radiusSlider = findViewById(R.id.searchRadiusSlider)

        //If you only want the slider start and end value and don't care about the previous values
        priceRangeSlider.addOnChangeListener { _, _, _ ->
            val values = priceRangeSlider.values
            priceLowerBound = values[0].toString()
            priceUpperBound = values[1].toString()
        }

        radiusSlider.addOnChangeListener{ _, _ , _->
            searchRadius = radiusSlider.value.toString()
        }


    }

}