package com.example.mealplanner.presentation.screen.shoppinglist

import com.example.mealplanner.domain.models.ShoppingItem

data class ShoppingUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val items: List<ShoppingItem> = emptyList()
)