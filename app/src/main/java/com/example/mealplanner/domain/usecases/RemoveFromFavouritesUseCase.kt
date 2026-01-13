package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.repositories.FavouritesRepository

class RemoveFromFavouritesUseCase(
    private val repository: FavouritesRepository
) {
    suspend operator fun invoke(recipeId: Int) {
        repository.removeFromFavourites(recipeId)
    }
}