package com.example.mealplanner.data.mappers

import com.example.mealplanner.data.local.shoplist.ShoppingItemEntity
import com.example.mealplanner.domain.models.Ingredient
import com.example.mealplanner.domain.models.ShoppingItem

fun ShoppingItemEntity.toDomainShoppingItem(): ShoppingItem = ShoppingItem(
    id = id,
    name = name,
    amount = amount,
    unit = unit,
    checked = checked
)

fun ShoppingItem.toDataShoppingItem(): ShoppingItemEntity {
    val cleanName = name.trim()
    return ShoppingItemEntity(
        id = id,
        name = cleanName,
        amount = amount,
        unit = unit,
        checked = checked,
        nameKey = cleanName.lowercase()
    )
}

fun Ingredient.toShoppingItemEntity(): ShoppingItemEntity {
    val cleanName = name.trim()
    return ShoppingItemEntity(
        name = cleanName,
        amount = amount,
        unit = unit,
        checked = false,
        nameKey = cleanName.lowercase()
    )
}