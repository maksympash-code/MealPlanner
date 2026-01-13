package com.example.mealplanner.domain.models

data class RecipeDetails(
    val recipe: Recipe,
    val ingredients: List<Ingredient>,
    val instructions: String?
)
