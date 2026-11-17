package ua.knu.maksym_pashchenko.mealplanner.data.mapper

import ua.knu.maksym_pashchenko.mealplanner.data.remote.dto.MealDto
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Ingredient
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

fun MealDto.toDomainRecipe() : Recipe {

    val ingredients = listOf(
        firstIngredient,
        secondIngredient,
        thirdIngredient,
        fourthIngredient,
        fifthIngredient,
        sixthIngredient,
        seventhIngredient,
        eighthIngredient,
        ninthIngredient,
        tenthIngredient,
        eleventhIngredient,
        twelfthIngredient,
        thirteenthIngredient,
        fourteenthIngredient,
        fifteenthIngredient,
        sixteenthIngredient,
        seventeenthIngredient,
        eighteenthIngredient,
        nineteenthIngredient,
        twentiethIngredient
    )

    val measures = listOf(
        firstMeasure,
        secondMeasure,
        thirdMeasure,
        fourthMeasure,
        fifthMeasure,
        sixthMeasure,
        seventhMeasure,
        eighthMeasure,
        ninthMeasure,
        tenthMeasure,
        eleventhMeasure,
        twelfthMeasure,
        thirteenthMeasure,
        fourteenthMeasure,
        fifteenthMeasure,
        sixteenthMeasure,
        seventeenthMeasure,
        eighteenthMeasure,
        nineteenthMeasure,
        twentiethMeasure
    )

    val fullIngredients = ingredients.zip(measures).mapNotNull { pair ->
        if (pair.first.isNullOrBlank()) {
            return@mapNotNull null
        }

        val trimmedName = pair.first?.trim().orEmpty()

        Ingredient(name = trimmedName, measure = pair.second?.trim().orEmpty())

    }

    return Recipe (
        id = id,
        name = name,
        category = category,
        area = area,
        instructions = instructions,
        imageUrl = image,
        ingredients = fullIngredients
    )
}