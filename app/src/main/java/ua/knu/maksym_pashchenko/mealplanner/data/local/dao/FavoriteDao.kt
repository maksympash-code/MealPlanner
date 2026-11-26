package ua.knu.maksym_pashchenko.mealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.FavoriteEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.relation.RecipeWithIngredients

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(recipe: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE recipeId = :recipeId")
    suspend fun deleteFavorite(recipeId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE recipeId = :recipeId)")
    fun isFavorite(recipeId: String): Flow<Boolean>

    @Transaction
    @Query("SELECT recipes.* FROM recipes INNER JOIN favorites on recipes.recipeId = favorites.recipeId")
    fun observeFavoriteRecipes(): Flow<List<RecipeWithIngredients>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE recipeId = :recipeId)")
    suspend fun isFavoriteOnce(recipeId: String): Boolean
}