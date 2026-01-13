package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.models.RecipeDetails
import com.example.mealplanner.domain.repositories.RecipesRepository

class GetRecipeDetailsUseCase(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(id: Int): RecipeDetails {
        return repository.getRecipeDetails(id)
    }
}