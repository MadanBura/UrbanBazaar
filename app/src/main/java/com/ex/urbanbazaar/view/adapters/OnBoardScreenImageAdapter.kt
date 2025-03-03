package com.ex.urbanbazaar.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ex.urbanbazaar.R

class OnBoardScreenImageAdapter(private val images: List<Int>) : RecyclerView.Adapter<OnBoardScreenImageAdapter.onBoardScreenImageViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onBoardScreenImageViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return onBoardScreenImageViewholder(view)
    }

    override fun onBindViewHolder(holder: onBoardScreenImageViewholder, position: Int) {
        val imageUrl = images[position]
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount() = images.size

    class onBoardScreenImageViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.productImageView)
    }
}