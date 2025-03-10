package com.ex.urbanbazaar.repository

import com.ex.urbanbazaar.model.response.CategoryResponse
import com.ex.urbanbazaar.model.response.ProductResponse
import retrofit2.Response

interface ProductRepository {

    suspend fun getProductCategories() :Response<CategoryResponse>
    suspend fun getProductList(categoryId:Int) : Response<ProductResponse>
    suspend fun getAllProducts() : Response<ProductResponse>
}