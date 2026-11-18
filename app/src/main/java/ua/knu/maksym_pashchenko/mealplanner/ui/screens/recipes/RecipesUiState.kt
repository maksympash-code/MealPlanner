package ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes

import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

data class RecipesUiState(
    val query: String = "",
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasSearched: Boolean = false
)
