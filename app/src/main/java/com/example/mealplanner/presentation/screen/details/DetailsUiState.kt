package com.example.mealplanner.presentation.screen.details

import com.example.mealplanner.domain.models.RecipeDetails

data class DetailsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val details: RecipeDetails? = null,
    val isFavourite: Boolean = false
)
