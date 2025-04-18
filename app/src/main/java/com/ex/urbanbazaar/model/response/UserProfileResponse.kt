package com.ex.urbanbazaar.model.response

data class UserProfileResponse(
    val avatar: String,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val role: String
)