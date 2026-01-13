package com.example.mealplanner.domain.repositories

import com.example.mealplanner.domain.models.Ingredient
import com.example.mealplanner.domain.models.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun getShoppingList(): Flow<List<ShoppingItem>>
    suspend fun addItem(item: ShoppingItem)
    suspend fun addIngredients(ingredients: List<Ingredient>)
    suspend fun toggleChecked(itemId: Int)
    suspend fun deleteItem(itemId: Int)
    suspend fun clearChecked()
}