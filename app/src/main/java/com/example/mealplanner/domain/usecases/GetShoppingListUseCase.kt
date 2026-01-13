package com.example.mealplanner.domain.usecases

import com.example.mealplanner.domain.models.ShoppingItem
import com.example.mealplanner.domain.repositories.ShoppingRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingListUseCase(
    private val repository: ShoppingRepository
) {
    operator fun invoke(): Flow<List<ShoppingItem>> {
        return repository.getShoppingList()
    }
}