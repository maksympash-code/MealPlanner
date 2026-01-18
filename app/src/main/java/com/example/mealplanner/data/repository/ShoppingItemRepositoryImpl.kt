package com.example.mealplanner.data.repository

import com.example.mealplanner.data.local.MealPlannerDataBase
import com.example.mealplanner.data.mappers.toDataShoppingItem
import com.example.mealplanner.data.mappers.toDomainShoppingItem
import com.example.mealplanner.data.mappers.toShoppingItemEntity
import com.example.mealplanner.domain.models.Ingredient
import com.example.mealplanner.domain.models.ShoppingItem
import com.example.mealplanner.domain.repositories.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingItemRepositoryImpl(
    private val db: MealPlannerDataBase
): ShoppingRepository {
    override fun getShoppingList(): Flow<List<ShoppingItem>> {
        return db.shopItemDao().observeShopping().map {
            entities -> entities.map { it.toDomainShoppingItem() }
        }
    }

    override suspend fun addItem(item: ShoppingItem) {
        db.shopItemDao().insertItem(item.toDataShoppingItem())
    }

    override suspend fun addIngredients(ingredients: List<Ingredient>) {
        val merged = ingredients
            .groupBy { it.name.trim().lowercase() }
            .map { (_, group) ->
                val first = group.first()
                first.copy(amount = group.sumOf { it.amount })
            }

        db.shopItemDao().insertAll(merged.map { it.toShoppingItemEntity() })
    }

    override suspend fun toggleChecked(itemId: Int) {
        db.shopItemDao().toggleChecked(itemId)
    }

    override suspend fun deleteItem(itemId: Int) {
        db.shopItemDao().deleteItem(itemId)
    }

    override suspend fun clearChecked() {
       db.shopItemDao().clearChecked()
    }
}