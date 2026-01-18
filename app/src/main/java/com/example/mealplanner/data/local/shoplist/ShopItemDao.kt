package com.example.mealplanner.data.local.shoplist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemDao {
    @Query("SELECT * FROM shopping_items ORDER BY id DESC")
    fun observeShopping(): Flow<List<ShoppingItemEntity>>

    @Insert
    suspend fun insertItem(item: ShoppingItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ShoppingItemEntity>)

    @Query("UPDATE shopping_items SET checked = NOT checked WHERE id = :itemId")
    suspend fun toggleChecked(itemId: Int)

    @Query("DELETE FROM shopping_items WHERE checked=1")
    suspend fun clearChecked()

    @Query("DELETE FROM shopping_items WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int)
}