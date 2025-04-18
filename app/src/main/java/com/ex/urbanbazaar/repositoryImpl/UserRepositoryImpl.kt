package com.ex.urbanbazaar.repositoryImpl

import com.ex.urbanbazaar.dataSource.remote.UrbanApiService
import com.ex.urbanbazaar.model.request.LoginRequest
import com.ex.urbanbazaar.model.request.UserRequest
import com.ex.urbanbazaar.model.response.ImageUploadResponse
import com.ex.urbanbazaar.model.response.RegistrationResponse
import com.ex.urbanbazaar.model.response.TokenResponse
import com.ex.urbanbazaar.model.response.UserProfileResponse
import com.ex.urbanbazaar.repository.UserRepository
import com.ex.urbanbazaar.utils.TokenManager
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val urbanApiService: UrbanApiService,
    private val tokenManager: TokenManager
) : UserRepository {


    override suspend fun saveUser(userRequest: UserRequest): Response<RegistrationResponse> {

        val response = urbanApiService.registerUser(userRequest)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun uploadImageToServer(file: MultipartBody.Part): Response<ImageUploadResponse> {
        val response = urbanApiService.uploadImageToServer(file)
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun loginUser(loginRequest: LoginRequest): Response<TokenResponse> {
        val response = urbanApiService.login(loginRequest)
        return if (response.isSuccessful) {
            response.body()?.let { tokenResponse ->
                // ✅ Save access & refresh tokens in TokenManager
                tokenManager.saveTokens(tokenResponse.access_token, tokenResponse.refresh_token)
                Response.success(tokenResponse)
            } ?: Response.error(response.code(), response.errorBody()!!)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun getUserProfileDetails(accessToken: String): Response<UserProfileResponse> {
        return urbanApiService.getUserProfile("Bearer $accessToken")
    }
}