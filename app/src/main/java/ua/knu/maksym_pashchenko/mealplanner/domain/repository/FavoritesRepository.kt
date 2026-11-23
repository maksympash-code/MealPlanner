package ua.knu.maksym_pashchenko.mealplanner.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

interface FavoritesRepository {

    suspend fun addFavorite(recipe: Recipe)

    suspend fun removeFavorite(recipeId: String)

    fun observeFavorites(): Flow<List<Recipe>>

    fun isFavorite(recipeId: String): Flow<Boolean>
}