package com.ex.urbanbazaar.dataSource.remote

import com.ex.urbanbazaar.model.request.AccessTokenRequest
import com.ex.urbanbazaar.model.request.LoginRequest
import com.ex.urbanbazaar.model.request.UserRequest
import com.ex.urbanbazaar.model.response.CategoryResponse
import com.ex.urbanbazaar.model.response.ProductResponse
import com.ex.urbanbazaar.model.response.ImageUploadResponse
import com.ex.urbanbazaar.model.response.RegistrationResponse
import com.ex.urbanbazaar.model.response.TokenResponse
import com.ex.urbanbazaar.model.response.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UrbanApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : Response<TokenResponse>

    @GET("auth/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): Response<UserProfileResponse>


    @POST("auth/refresh-token")
    fun refreshToken(
        @Body accessTokenRequest: AccessTokenRequest
    ) : Response<TokenResponse>

    @POST("users/")
     suspend fun registerUser(
        @Body userRequest: UserRequest
    ) : Response<RegistrationResponse>

    @Multipart
    @POST("files/upload")
    suspend fun uploadImageToServer(@Part file : MultipartBody.Part) : Response<ImageUploadResponse>

    @GET("categories")
    suspend fun getCategories() : Response<CategoryResponse>

    @GET("categories/{categoryID}/products")
    suspend fun getProductsByCategory(
        @Path("categoryID") categoryId: Int
    ): Response<ProductResponse>

    @GET("products")
    suspend fun getAllProducts() : Response<ProductResponse>


}