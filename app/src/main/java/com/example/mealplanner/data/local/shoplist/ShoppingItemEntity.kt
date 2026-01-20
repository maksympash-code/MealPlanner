package com.example.mealplanner.data.local.shoplist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_items",
    indices = [Index(value = ["nameKey"], unique = true)]
)
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("nameKey")
    val nameKey: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "unit")
    val unit: String?,

    @ColumnInfo(name = "checked")
    val checked: Boolean,
)
