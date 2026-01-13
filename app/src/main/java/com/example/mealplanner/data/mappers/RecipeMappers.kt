package com.example.mealplanner.data.mappers

import com.example.mealplanner.data.dto.RecipeDto
import com.example.mealplanner.domain.models.Recipe

fun RecipeDto.toDomainRecipe(): Recipe = Recipe (
    id = id ?: 0,
    title = title.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    readyMinutes = null,
    servings = null
)