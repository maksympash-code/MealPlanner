package com.example.mealplanner.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.repositories.FavouritesRepository
import com.example.mealplanner.domain.repositories.RecipesRepository
import com.example.mealplanner.presentation.screen.search.model.BottomTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipesRepo: RecipesRepository,
    private val favouritesRepo: FavouritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState>
        get() = _uiState

    val favourites: StateFlow<List<Recipe>> =
        favouritesRepo.getFavourites().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    fun onTabSelected(tab: BottomTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onQueryChanged(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
    }

    fun onSearchClick() {
        val query = uiState.value.query.trim()

        if (query.isBlank()) {
            _uiState.update { it.copy(error = "Type something first 🙂") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val result = recipesRepo.searchRecipes(query)
                _uiState.update { it.copy(recipes = result) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Unknown error") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}