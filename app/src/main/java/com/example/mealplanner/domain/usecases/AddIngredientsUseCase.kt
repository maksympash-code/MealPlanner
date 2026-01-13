package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.models.Ingredient
import com.example.mealplanner.domain.repositories.ShoppingRepository

class AddIngredientsUseCase(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(ingredients: List<Ingredient>) {
        repository.addIngredients(ingredients)
    }
}