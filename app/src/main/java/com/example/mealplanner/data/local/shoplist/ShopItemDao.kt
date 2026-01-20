package com.example.mealplanner.data.local.shoplist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemDao {
    @Query("SELECT * FROM shopping_items ORDER BY id DESC")
    fun observeShopping(): Flow<List<ShoppingItemEntity>>

    @Query("SELECT * FROM shopping_items WHERE nameKey = :key LIMIT 1")
    suspend fun getByKey(key: String): ShoppingItemEntity?

    @Insert
    suspend fun insertItem(item: ShoppingItemEntity)

    @Update
    suspend fun updateItem(item: ShoppingItemEntity)

    @Transaction
    suspend fun upsetMerge(items: List<ShoppingItemEntity>) {
        for (item in items) {
            val existing = getByKey(item.nameKey)
            if (existing == null) {
                insertItem(item)
            } else {
                val mergedUnit = existing.unit?.takeIf { it.isNotBlank() } ?: item.unit
                updateItem(
                    existing.copy(
                        amount = existing.amount + item.amount,
                        unit = mergedUnit
                    )
                )
            }
        }
    }

    @Query("UPDATE shopping_items SET checked = NOT checked WHERE id = :itemId")
    suspend fun toggleChecked(itemId: Int)

    @Query("DELETE FROM shopping_items WHERE checked=1")
    suspend fun clearChecked()

    @Query("DELETE FROM shopping_items WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int)
}