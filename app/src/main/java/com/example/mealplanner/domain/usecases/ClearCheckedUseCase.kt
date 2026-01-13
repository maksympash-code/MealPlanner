package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.repositories.ShoppingRepository

class ClearCheckedUseCase(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke() {
        repository.clearChecked()
    }
}