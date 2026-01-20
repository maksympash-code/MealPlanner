package com.example.mealplanner.presentation.screen.search

import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.presentation.screen.search.model.BottomTab

data class SearchUiState(
    val selectedTab: BottomTab = BottomTab.SEARCH,
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val recipes: List<Recipe> = emptyList(),
)
