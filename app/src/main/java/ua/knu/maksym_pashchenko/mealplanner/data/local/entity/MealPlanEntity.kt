package ua.knu.maksym_pashchenko.mealplanner.data.local.entity

import androidx.room.Entity
import ua.knu.maksym_pashchenko.mealplanner.domain.types.MealType
import java.time.LocalDate

@Entity(tableName = "meal_plans", primaryKeys = ["date", "mealType"])
data class MealPlanEntity(
    val date: LocalDate,
    val mealType: MealType,
    val recipeId: String
)
