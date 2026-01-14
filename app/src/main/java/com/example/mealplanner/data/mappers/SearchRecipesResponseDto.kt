package com.example.mealplanner.data.mappers

import com.example.mealplanner.data.dto.SearchRecipesResponseDto
import com.example.mealplanner.domain.models.Recipe

fun SearchRecipesResponseDto.toDomainListRecipes(): List<Recipe> =
    recipes?.map { it.toDomainRecipe() } ?: emptyList()
