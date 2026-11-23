package ua.knu.maksym_pashchenko.mealplanner.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.IngredientEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.RecipeEntity

data class RecipeWithIngredients(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientEntity>
)
