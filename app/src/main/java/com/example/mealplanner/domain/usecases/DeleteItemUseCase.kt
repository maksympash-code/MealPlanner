package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.repositories.ShoppingRepository

class DeleteItemUseCase(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(itemId: Int) {
        repository.deleteItem(itemId)
    }
}