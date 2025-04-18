package com.ex.urbanbazaar.model.response

data class CategoryResponseItem(
    val creationAt: String,
    val id: Int,
    val image: String,
    val name: String,
    val slug: String,
    val updatedAt: String
)