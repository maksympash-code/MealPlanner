package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.models.ShoppingItem
import com.example.mealplanner.domain.repositories.ShoppingRepository

class AddItemUseCase(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(item: ShoppingItem) {
        repository.addItem(item)
    }
}