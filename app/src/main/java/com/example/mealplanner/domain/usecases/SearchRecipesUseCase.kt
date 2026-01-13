package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.repositories.RecipesRepository

class SearchRecipesUseCase(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(query: String): List<Recipe>{
        return repository.searchRecipes(query)
    }
}