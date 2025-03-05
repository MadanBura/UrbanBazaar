package com.ex.urbanbazaar.repository

import com.ex.urbanbazaar.model.response.CategoryResponse
import retrofit2.Response

interface ProductRepository {

    suspend fun getProductCategories() : Response<CategoryResponse>

}