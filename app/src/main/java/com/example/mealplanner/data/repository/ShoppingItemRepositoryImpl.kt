package com.example.mealplanner.data.repository

import com.example.mealplanner.data.local.shoplist.ShopItemDao
import com.example.mealplanner.data.mappers.toDataShoppingItem
import com.example.mealplanner.data.mappers.toDomainShoppingItem
import com.example.mealplanner.data.mappers.toShoppingItemEntity
import com.example.mealplanner.domain.models.Ingredient
import com.example.mealplanner.domain.models.ShoppingItem
import com.example.mealplanner.domain.repositories.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingItemRepositoryImpl @Inject constructor(
    private val dao: ShopItemDao
): ShoppingRepository {
    override fun getShoppingList(): Flow<List<ShoppingItem>> {
        return dao.observeShopping().map {
            entities -> entities.map { it.toDomainShoppingItem() }
        }
    }

    override suspend fun addItem(item: ShoppingItem) {
        dao.upsetMerge(listOf(item.toDataShoppingItem()))
    }

    override suspend fun addIngredients(ingredients: List<Ingredient>) {
        val merged = ingredients
            .groupBy { it.name.trim().lowercase() }
            .map { (_, group) ->
                val first = group.first()
                first.copy(amount = group.sumOf { it.amount })
            }

        dao.upsetMerge(merged.map { it.toShoppingItemEntity() })
    }

    override suspend fun toggleChecked(itemId: Int) {
        dao.toggleChecked(itemId)
    }

    override suspend fun deleteItem(itemId: Int) {
        dao.deleteItem(itemId)
    }

    override suspend fun clearChecked() {
       dao.clearChecked()
    }
}