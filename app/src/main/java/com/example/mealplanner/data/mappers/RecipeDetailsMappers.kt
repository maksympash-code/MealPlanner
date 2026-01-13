package com.example.mealplanner.data.mappers

import com.example.mealplanner.data.dto.IngredientDto
import com.example.mealplanner.data.dto.RecipeDetailsDto
import com.example.mealplanner.domain.models.Ingredient
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.domain.models.RecipeDetails

fun IngredientDto.toDomainIngredient(): Ingredient = Ingredient (
    name = name.orEmpty(),
    amount = amount ?: 0.0,
    unit = unit
)

fun RecipeDetailsDto.toDomainRecipeDetails(): RecipeDetails = RecipeDetails (
    recipe = Recipe (
        id = id ?: 0,
        title = title.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        readyMinutes = readyMinutes,
        servings = servings
    ),
    instructions = instructions,
    ingredients = ingredients?.map { it.toDomainIngredient() } ?: emptyList()
)
