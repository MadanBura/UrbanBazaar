package com.ex.urbanbazaar.repository

import com.ex.urbanbazaar.model.request.LoginRequest
import com.ex.urbanbazaar.model.request.UserRequest
import com.ex.urbanbazaar.model.response.ImageUploadResponse
import com.ex.urbanbazaar.model.response.RegistrationResponse
import com.ex.urbanbazaar.model.response.TokenResponse
import com.ex.urbanbazaar.model.response.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface UserRepository  {

    suspend fun saveUser(userRequest: UserRequest) : Response<RegistrationResponse>
    suspend fun uploadImageToServer(file : MultipartBody.Part) : Response<ImageUploadResponse>
    suspend fun loginUser(loginRequest: LoginRequest) : Response<TokenResponse>
    suspend fun getUserProfileDetails(accessToken : String) : Response<UserProfileResponse>
}