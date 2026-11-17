package ua.knu.maksym_pashchenko.mealplanner.domain.repository

import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

interface RecipeRepository {

    suspend fun searchRecipes(query: String) : List<Recipe>

    suspend fun getRecipeById(id: String) : Recipe?

    suspend fun getRandomRecipe() : Recipe?
}