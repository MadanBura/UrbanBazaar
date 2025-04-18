package com.ex.urbanbazaar.model.request

import com.google.gson.annotations.SerializedName

data class AccessTokenRequest(
    @SerializedName("refreshToken")
    private val refreshToken : String
)