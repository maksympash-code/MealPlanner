package com.example.mealplanner.domain.models

data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val readyMinutes: Int?,
    val servings: Int?
)