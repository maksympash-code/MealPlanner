package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.repositories.FavouritesRepository

class AddToFavouritesUseCase(
    private val repository: FavouritesRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        repository.addToFavourites(recipe)
    }
}