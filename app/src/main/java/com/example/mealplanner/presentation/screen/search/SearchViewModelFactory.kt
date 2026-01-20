package com.example.mealplanner.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.domain.repositories.FavouritesRepository
import com.example.mealplanner.domain.repositories.RecipesRepository

class SearchViewModelFactory(
    private val recipesRepo: RecipesRepository,
    private val favouritesRepo: FavouritesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(recipesRepo, favouritesRepo) as T
    }
}