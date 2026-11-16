package ua.knu.maksym_pashchenko.mealplanner.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealResponseDto(
    @SerialName("meals")
    val meals: List<MealDto>?
)