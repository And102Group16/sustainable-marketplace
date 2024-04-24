package com.example.sustainify

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val items: MutableList<Item>, private val role: String, private val context: Context) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headingTextView: TextView = itemView.findViewById(R.id.textViewHeading)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        val priceTextView: TextView = itemView.findViewById(R.id.textViewPrice)
        val viewMoreButton: TextView = itemView.findViewById(R.id.textViewMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.headingTextView.text = item.heading
        holder.descriptionTextView.text = item.description
        holder.priceTextView.text = "$${item.price}"

        holder.viewMoreButton.setOnClickListener {
            // Handle view more button click
            val intent = Intent(context, ProductDetailFragment::class.java)
            intent.putExtra("item", item)
            context.startActivity(intent)
        }
    }

    fun addItem(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        fun notifyItemRangeInserted(adapter: RecyclerView.Adapter<*>, positionStart: Int, itemCount: Int) {
            adapter.notifyItemRangeInserted(positionStart, itemCount)
        }
    }
}
