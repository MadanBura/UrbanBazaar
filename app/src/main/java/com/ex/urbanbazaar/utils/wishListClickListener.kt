package com.ex.urbanbazaar.utils

import com.ex.urbanbazaar.model.response.CategoryResponseItem
import com.ex.urbanbazaar.model.response.ProductResponseItem

interface wishListClickListener {

    fun clickToAddWishList(product : ProductResponseItem)

}