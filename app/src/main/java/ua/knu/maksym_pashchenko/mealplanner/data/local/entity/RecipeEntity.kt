package ua.knu.maksym_pashchenko.mealplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val recipeId: String,
    val recipeName: String,
    val category: String?,
    val area: String?,
    val imageUrl: String?,
    val instructions: String?
)