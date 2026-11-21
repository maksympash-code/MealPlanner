package ua.knu.maksym_pashchenko.mealplanner.ui.screens.details.state

import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

sealed interface RecipeDetailsUiState {
    data object Loading : RecipeDetailsUiState

    data class Success(
        val recipe: Recipe
    ) : RecipeDetailsUiState

    data class Error(
        val message: String
    ) : RecipeDetailsUiState
}