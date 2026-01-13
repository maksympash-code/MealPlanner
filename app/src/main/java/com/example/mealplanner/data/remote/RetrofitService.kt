package com.example.mealplanner.data.remote

import com.example.mealplanner.data.dto.RecipeDetailsDto
import com.example.mealplanner.data.dto.SearchRecipesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(@Query("apiKey") apiKey: String, @Query("query") query: String): SearchRecipesResponseDto

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(@Path("id") id: Int, @Query("apiKey") apiKey: String): RecipeDetailsDto
}