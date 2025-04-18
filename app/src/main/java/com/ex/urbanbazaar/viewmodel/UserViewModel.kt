package com.ex.urbanbazaar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.urbanbazaar.model.request.LoginRequest
import com.ex.urbanbazaar.model.request.UserRequest
import com.ex.urbanbazaar.model.response.ImageUploadResponse
import com.ex.urbanbazaar.model.response.RegistrationResponse
import com.ex.urbanbazaar.model.response.UserProfileResponse
import com.ex.urbanbazaar.repository.UserRepository
import com.ex.urbanbazaar.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registrationRes = MutableLiveData<ResultState<RegistrationResponse>>()
    val registrationRes: LiveData<ResultState<RegistrationResponse>> get() = _registrationRes

    private val _ImageUploadRes = MutableLiveData<ResultState<ImageUploadResponse>>()
    val ImageUploadRes: LiveData<ResultState<ImageUploadResponse>> get() = _ImageUploadRes

    private val _loginState = MutableLiveData<ResultState<UserProfileResponse>>()
    val loginState : LiveData<ResultState<UserProfileResponse>> get() = _loginState


    fun registerUser(userRequest: UserRequest) {
        _registrationRes.value = ResultState.Loading
        viewModelScope.launch {
            try {
                val response = userRepository.saveUser(userRequest)

                if (response.isSuccessful && response.body() != null) {
                    _registrationRes.value = ResultState.Success(response.body()!!)
                } else {
                    _registrationRes.value =
                        ResultState.Error(Throwable("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _registrationRes.value = ResultState.Error(e)
            }
        }
    }



    fun uploadImageToServer(file : File){
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        viewModelScope.launch {
            try {
                val response = userRepository.uploadImageToServer(body)
                if (response.isSuccessful && response.body() != null) {
                    _ImageUploadRes.value = ResultState.Success(response.body()!!)
                } else {
                    _ImageUploadRes.value =
                        ResultState.Error(Throwable("Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _ImageUploadRes.value = ResultState.Error(e)
            }
        }

    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val loginResponse = userRepository.loginUser(LoginRequest(email, password))
            if (loginResponse.isSuccessful) {
                val accessToken = loginResponse.body()?.access_token
                if (!accessToken.isNullOrEmpty()) {
                    val profileResponse = userRepository.getUserProfileDetails(accessToken)
                    _loginState.value = if (profileResponse.isSuccessful) {
                        ResultState.Success(profileResponse.body()!!)
                    } else {
                        ResultState.Error(Exception("Failed to fetch profile: ${profileResponse.message()}"))
                    }
                } else {
                    _loginState.value = ResultState.Error(Exception("Access token is null"))
                }
            } else {
                _loginState.value = ResultState.Error(Exception("Login failed: ${loginResponse.message()}"))
            }
        }
    }

}