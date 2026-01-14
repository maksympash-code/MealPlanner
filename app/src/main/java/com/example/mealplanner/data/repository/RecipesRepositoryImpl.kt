package com.example.mealplanner.data.repository

import com.example.mealplanner.data.mappers.toDomainListRecipes
import com.example.mealplanner.data.mappers.toDomainRecipeDetails
import com.example.mealplanner.data.remote.RetrofitService
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.models.RecipeDetails
import com.example.mealplanner.domain.repositories.RecipesRepository

class RecipesRepositoryImpl(
    private val api: RetrofitService,
    private val apiKey: String = "bd224a9efcc143adada81c6de026e336"
): RecipesRepository {
    override suspend fun searchRecipes(query: String): List<Recipe> =
        api.searchRecipes(apiKey, query).toDomainListRecipes()

    override suspend fun getRecipeDetails(id: Int): RecipeDetails =
        api.getRecipeDetails(id, apiKey).toDomainRecipeDetails()
}