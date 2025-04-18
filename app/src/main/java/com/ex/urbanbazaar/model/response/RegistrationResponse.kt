package com.ex.urbanbazaar.model.response

data class RegistrationResponse(
    val avatar: String,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val role: String
)