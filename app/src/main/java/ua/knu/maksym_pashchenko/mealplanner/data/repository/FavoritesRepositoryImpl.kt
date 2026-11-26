package ua.knu.maksym_pashchenko.mealplanner.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.knu.maksym_pashchenko.mealplanner.data.local.AppDatabase
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.FavoriteDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.IngredientDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.RecipeDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.FavoriteEntity
import ua.knu.maksym_pashchenko.mealplanner.data.mapper.toDomain
import ua.knu.maksym_pashchenko.mealplanner.data.mapper.toEntity
import ua.knu.maksym_pashchenko.mealplanner.data.mapper.toIngredientEntities
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val favoriteDao: FavoriteDao,
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao
) : FavoritesRepository {
    override suspend fun addFavorite(recipe: Recipe) {
        database.withTransaction {
            saveFavoriteInternal(recipe)
        }
    }

    override suspend fun removeFavorite(recipeId: String) {
        favoriteDao.deleteFavorite(recipeId)
    }

    override fun observeFavorites(): Flow<List<Recipe>> {
        return favoriteDao.observeFavoriteRecipes()
            .map { listRecipeWithIngredients ->
                listRecipeWithIngredients.map { recipeWithIngredients ->
                    recipeWithIngredients.toDomain()
                }
            }
    }

    override fun isFavorite(recipeId: String): Flow<Boolean> {
        return favoriteDao.isFavorite(recipeId)
    }

    override suspend fun toggleFavorite(recipe: Recipe) {
        database.withTransaction {
            val status = favoriteDao.isFavoriteOnce(recipe.id)

            if (status) {
                removeFavorite(recipe.id)
            } else {
                saveFavoriteInternal(recipe)
            }
        }
    }

    private suspend fun saveFavoriteInternal(recipe: Recipe) {
        recipeDao.insertRecipe(recipe.toEntity())

        ingredientDao.deleteIngredientsByRecipeId(recipe.id)

        ingredientDao.insertIngredients(recipe.toIngredientEntities())

        favoriteDao.insertFavorite(FavoriteEntity(recipeId = recipe.id))
    }
}