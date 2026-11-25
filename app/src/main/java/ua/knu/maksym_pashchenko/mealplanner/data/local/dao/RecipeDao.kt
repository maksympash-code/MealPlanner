package ua.knu.maksym_pashchenko.mealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.RecipeEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.relation.RecipeWithIngredients

@Dao
interface RecipeDao {

    @Upsert
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE recipeId = :id")
    suspend fun getRecipeById(id: String): RecipeEntity?

    @Transaction
    @Query("SELECT * FROM recipes WHERE recipeId = :recipeId")
    suspend fun getRecipeWithIngredientsById(recipeId: String) : RecipeWithIngredients?
}