package com.example.travelmatentt.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelmatentt.R

class CardAdapter(private val context: Context, private var items: List<String>, private var imageResources: List<Int>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCardImage: ImageView = view.findViewById(R.id.ivCardImage)
        val tvCardTitle: TextView = view.findViewById(R.id.tvCardTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val title = items[position]
        val imageResId = imageResources[position]

        holder.tvCardTitle.text = title
        holder.ivCardImage.setImageResource(imageResId)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newItems: List<String>, newImages: List<Int>) {
        items = newItems
        imageResources = newImages
        notifyDataSetChanged()
    }

}



