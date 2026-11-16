package ua.knu.maksym_pashchenko.mealplanner.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    @SerialName("idMeal")
    val id: String,

    @SerialName("strMeal")
    val name: String,

    @SerialName("strCategory")
    val category: String?,

    @SerialName("strArea")
    val area: String?,

    @SerialName("strInstructions")
    val instruction: String?,

    @SerialName("strMealThumb")
    val image: String?,

    @SerialName("strIngredient1")
    val firstIngredient: String,

    @SerialName("strMeasure1")
    val firstMeasure: String
)