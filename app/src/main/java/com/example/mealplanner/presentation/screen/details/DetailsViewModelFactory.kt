package com.example.mealplanner.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.domain.repositories.FavouritesRepository
import com.example.mealplanner.domain.repositories.RecipesRepository
import com.example.mealplanner.domain.repositories.ShoppingRepository

class DetailsViewModelFactory(
    private val recipesRepo: RecipesRepository,
    private val favouritesRepo: FavouritesRepository,
    private val shoppingRepo: ShoppingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(recipesRepo, favouritesRepo, shoppingRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}