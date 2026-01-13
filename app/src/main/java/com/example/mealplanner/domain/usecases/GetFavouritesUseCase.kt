package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.repositories.FavouritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavouritesUseCase(
    private val repository: FavouritesRepository
) {
    operator fun invoke(): Flow<List<Recipe>> {
        return repository.getFavourites()
    }
}