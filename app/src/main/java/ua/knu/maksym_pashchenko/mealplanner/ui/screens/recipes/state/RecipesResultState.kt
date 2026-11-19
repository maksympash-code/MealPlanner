package ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes.state

import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

sealed interface RecipesResultState {
    data object Initial : RecipesResultState
    data object Loading : RecipesResultState

    data class Success(
        val recipes: List<Recipe>
    ) : RecipesResultState

    data object Empty : RecipesResultState

    data class Error (
        val message: String
    ) : RecipesResultState
}