package ua.knu.maksym_pashchenko.mealplanner.data.mapper

import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.IngredientEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.RecipeEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.relation.RecipeWithIngredients
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Ingredient
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

fun Recipe.toEntity(): RecipeEntity = RecipeEntity(
    recipeId = id,
    recipeName = name,
    category = category,
    area = area,
    imageUrl = imageUrl,
    instructions = instructions
)

fun Recipe.toIngredientEntities(): List<IngredientEntity> {
    return ingredients.map { ingredient ->
        IngredientEntity(
            recipeId = id,
            ingredientName = ingredient.name,
            ingredientMeasure = ingredient.measure
        )
    }
}

fun RecipeWithIngredients.toDomain(): Recipe = Recipe(
    id = recipe.recipeId,
    name = recipe.recipeName,
    category = recipe.category,
    area = recipe.area,
    instructions = recipe.instructions,
    imageUrl = recipe.imageUrl,
    ingredients = ingredients.map { ingredientEntity ->
        Ingredient(
            name = ingredientEntity.ingredientName,
            measure = ingredientEntity.ingredientMeasure
        )
    }
)