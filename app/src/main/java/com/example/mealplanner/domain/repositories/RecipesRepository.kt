package com.example.mealplanner.domain.repositories

import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.models.RecipeDetails

interface RecipesRepository {
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun getRecipeDetails(id: Int): RecipeDetails
}