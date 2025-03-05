package com.ex.urbanbazaar.model.request

data class UserRequest(
    val avatar: String,
    val email: String,
    val name: String,
    val password: String
)