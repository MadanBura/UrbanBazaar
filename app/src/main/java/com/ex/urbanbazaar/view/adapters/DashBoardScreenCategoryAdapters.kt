package com.ex.urbanbazaar.view.adapters

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.ex.urbanbazaar.model.response.CategoryResponseItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ex.urbanbazaar.R
import com.ex.urbanbazaar.model.response.ProductResponseItem

class DashBoardScreenCategoryAdapters(
    private var list: List<CategoryResponseItem>
) : RecyclerView.Adapter<DashBoardScreenCategoryAdapters.CategoryViewClassHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewClassHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoryViewClassHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewClassHolder, position: Int) {

        val categoryObj = list[position]

        Glide.with(holder.itemView.context)
            .load(categoryObj.image)
            .circleCrop()
            .into(holder.categoryImage)

        holder.categoryName.text = categoryObj.name
    }

    override fun getItemCount() = list.size

    inner class CategoryViewClassHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryImage : ImageView = view.findViewById(R.id.categoryIcon)
        val categoryName : TextView = view.findViewById(R.id.categoryName)
    }


    fun updateCategories(newProducts: List<CategoryResponseItem>) {
        if (newProducts.isNotEmpty()) {
            list = newProducts
            notifyDataSetChanged()
        } else {
            Log.e("ProductAdapter", "Trying to update adapter with an empty list")
        }
    }


}