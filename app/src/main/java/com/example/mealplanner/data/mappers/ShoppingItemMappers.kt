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

fun ShoppingItem.toDataShoppingItem(): ShoppingItemEntity = ShoppingItemEntity(
    id = id,
    name = name,
    amount = amount,
    unit = unit,
    checked = checked
)

fun Ingredient.toShoppingItemEntity(): ShoppingItemEntity = ShoppingItemEntity(
    name = name,
    amount = amount,
    unit = unit,
    checked = false
)