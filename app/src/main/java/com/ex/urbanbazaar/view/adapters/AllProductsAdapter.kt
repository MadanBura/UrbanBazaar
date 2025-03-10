package com.ex.urbanbazaar.view.adapters

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.ex.urbanbazaar.R
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ex.urbanbazaar.model.response.ProductResponseItem
import com.ex.urbanbazaar.utils.wishListClickListener

class AllProductsAdapter(
    private var list: List<ProductResponseItem>,
    private val click : wishListClickListener
) : RecyclerView.Adapter<AllProductsAdapter.ProdcutsViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdcutsViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_product_image, parent, false)
        return ProdcutsViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ProdcutsViewHolderClass, position: Int) {

        val clotheItem = list[position]

        if (clotheItem.images.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(clotheItem.images[0])
                .into(holder.productImage)
        }

        holder.productName.text = clotheItem.title
        holder.productPrice.text = clotheItem.price.toString()

        holder.wishListBtn.setOnClickListener {
            click.clickToAddWishList(clotheItem)
        }
    }

    override fun getItemCount() = list.size

    inner class ProdcutsViewHolderClass(view : View) : RecyclerView.ViewHolder(view){
        val productImage : ImageView = view.findViewById(R.id.productImageClothes)
        val productName : TextView = view.findViewById(R.id.itemName)
        val productPrice : TextView = view.findViewById(R.id.itemPrice)
        val wishListBtn : ImageView = view.findViewById(R.id.wishListBtn)
    }

    fun updateProducts(newProducts: List<ProductResponseItem>) {
        if (newProducts.isNotEmpty()) {
            list = newProducts
            notifyDataSetChanged()
        } else {
            Log.e("ProductAdapter", "Trying to update adapter with an empty list")
        }
    }


}