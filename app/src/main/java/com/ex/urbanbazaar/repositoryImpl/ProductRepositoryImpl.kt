package com.ex.urbanbazaar.repositoryImpl

import com.ex.urbanbazaar.dataSource.remote.UrbanApiService
import com.ex.urbanbazaar.model.response.CategoryResponse
import com.ex.urbanbazaar.model.response.ProductResponse
import com.ex.urbanbazaar.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val urbanApiService: UrbanApiService
) : ProductRepository {

    override suspend fun getProductCategories(): Response<CategoryResponse> {
        val response = urbanApiService.getCategories()
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun getAllProducts(): Response<ProductResponse> {
        val response = urbanApiService.getAllProducts()
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun getProductList(categoryId: Int): Response<ProductResponse> {
        val response = urbanApiService.getProductsByCategory(categoryId)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}