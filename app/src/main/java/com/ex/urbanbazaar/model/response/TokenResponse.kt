package com.ex.urbanbazaar.model.response

data class LoginResponse(
    val access_token: String,
    val refresh_token: String
)