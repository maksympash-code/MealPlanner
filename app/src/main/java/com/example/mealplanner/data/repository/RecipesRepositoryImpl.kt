package com.example.mealplanner.data.repository

import com.example.mealplanner.data.mappers.toDomainListRecipes
import com.example.mealplanner.data.mappers.toDomainRecipeDetails
import com.example.mealplanner.data.remote.RetrofitService
import com.example.mealplanner.di.ApiKey
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.models.RecipeDetails
import com.example.mealplanner.domain.repositories.RecipesRepository
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val api: RetrofitService,
    @ApiKey private val apiKey: String
): RecipesRepository {
    override suspend fun searchRecipes(query: String): List<Recipe> =
        api.searchRecipes(apiKey, query).toDomainListRecipes()

    override suspend fun getRecipeDetails(id: Int): RecipeDetails =
        api.getRecipeDetails(id, apiKey).toDomainRecipeDetails()
}