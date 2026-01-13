package com.example.mealplanner.domain.models

data class ShoppingItem(
    val id: Int,
    val name: String,
    val amount: Double,
    val unit: String?,
    val checked: Boolean,
)
