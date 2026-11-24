package ua.knu.maksym_pashchenko.mealplanner.ui.screens.favorites.state

import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

sealed interface FavoritesUiState {

    data object Loading: FavoritesUiState

    data object Empty: FavoritesUiState

    data class Success(
        val favorites: List<Recipe>
    ) : FavoritesUiState

    data class Error(
        val message: String
    ) : FavoritesUiState
}