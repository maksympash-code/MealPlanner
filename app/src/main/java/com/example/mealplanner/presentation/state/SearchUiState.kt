package com.example.mealplanner.presentation.state

import com.example.mealplanner.domain.models.Recipe

sealed class SearchUiState {
    data object Idle : SearchUiState()
    data object Loading : SearchUiState()
    data class Content(val recipes: List<Recipe>) : SearchUiState()
    data object Empty : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}