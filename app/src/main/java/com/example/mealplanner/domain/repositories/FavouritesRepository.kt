package com.example.mealplanner.domain.repositories

import com.example.mealplanner.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getFavourites(): Flow<List<Recipe>>
    suspend fun addToFavourites(recipe: Recipe)
    suspend fun removeFromFavourites(recipeId: Int)
}