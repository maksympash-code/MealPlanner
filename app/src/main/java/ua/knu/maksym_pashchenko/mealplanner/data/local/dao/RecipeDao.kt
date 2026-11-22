package ua.knu.maksym_pashchenko.mealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.RecipeEntity

@Dao
interface RecipeDao {

    @Upsert
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE recipeId = :id")
    suspend fun getRecipeById(id: String): RecipeEntity?
}