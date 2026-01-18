package com.example.mealplanner.data.mappers

import com.example.mealplanner.data.local.favourites.FavouriteRecipeEntity
import com.example.mealplanner.domain.models.Recipe

fun FavouriteRecipeEntity.toDomainRecipe(): Recipe = Recipe(
    id = id,
    title = title ?: "",
    imageUrl = imageUrl ?: "",
    readyMinutes = readyMinutes,
    servings = servings
)

fun Recipe.toDataFavouriteEntity(): FavouriteRecipeEntity = FavouriteRecipeEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    readyMinutes = readyMinutes,
    servings = servings
)