package com.ex.urbanbazaar.model.response

data class TokenResponse(
    val access_token: String,
    val refresh_token: String
)