package com.example.mealplanner.data.repository

import com.example.mealplanner.data.local.FavouritesDao
import com.example.mealplanner.data.mappers.toDataFavouriteEntity
import com.example.mealplanner.data.mappers.toDomainRecipe
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.repositories.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouritesRepositoryImpl(
    private val dao: FavouritesDao,
): FavouritesRepository {
    override fun getFavourites(): Flow<List<Recipe>> {
        return dao.observeFavourites().map {
            entities -> entities.map { it.toDomainRecipe() }
        }
    }

    override suspend fun addToFavourites(recipe: Recipe) {
        dao.addFavourite(recipe.toDataFavouriteEntity())
    }

    override suspend fun removeFromFavourites(recipeId: Int) {
        dao.removeFavourite(recipeId)
    }

}