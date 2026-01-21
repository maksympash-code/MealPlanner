package com.example.mealplanner.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.repositories.FavouritesRepository
import com.example.mealplanner.domain.repositories.RecipesRepository
import com.example.mealplanner.domain.repositories.ShoppingRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val recipesRepo: RecipesRepository,
    private val favouritesRepo: FavouritesRepository,
    private val shoppingRepo: ShoppingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState>
        get() = _uiState

    private var currentRecipeId = 0
    private var favJob: Job? = null

    fun load(recipeId: Int) {
        currentRecipeId = recipeId

        favJob?.cancel()
        favJob = viewModelScope.launch {
            favouritesRepo.getFavourites().collect { recipeList ->
                _uiState.update { it.copy(isFavourite = recipeList.any { r -> r.id == recipeId }) }
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val details = recipesRepo.getRecipeDetails(recipeId)
                _uiState.update { it.copy(details = details) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Unknown error") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onToggleFavouriteClick() {
        val details = _uiState.value.details ?: return
        viewModelScope.launch {
            if (_uiState.value.isFavourite) favouritesRepo.removeFromFavourites(details.recipe.id)
            else favouritesRepo.addToFavourites(details.recipe)
        }
    }

    fun onAddIngredientsClick() {
        val details = _uiState.value.details ?: return
        viewModelScope.launch {
            try {
                shoppingRepo.addIngredients(details.ingredients)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Failed to add ingredients") }
            }
        }
    }
}