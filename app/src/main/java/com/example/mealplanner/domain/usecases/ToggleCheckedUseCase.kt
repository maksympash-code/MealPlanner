package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.repositories.ShoppingRepository

class ToggleCheckedUseCase(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(itemId: Int) {
        repository.toggleChecked(itemId)
    }
}