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
    val instructions: String?,

    @SerialName("strMealThumb")
    val image: String?,

    @SerialName("strIngredient1")
    val firstIngredient: String?,

    @SerialName("strIngredient2")
    val secondIngredient: String?,

    @SerialName("strIngredient3")
    val thirdIngredient: String?,

    @SerialName("strIngredient4")
    val fourthIngredient: String?,

    @SerialName("strIngredient5")
    val fifthIngredient: String?,

    @SerialName("strIngredient6")
    val sixthIngredient: String?,

    @SerialName("strIngredient7")
    val seventhIngredient: String?,

    @SerialName("strIngredient8")
    val eighthIngredient: String?,

    @SerialName("strIngredient9")
    val ninthIngredient: String?,

    @SerialName("strIngredient10")
    val tenthIngredient: String?,

    @SerialName("strIngredient11")
    val eleventhIngredient: String?,

    @SerialName("strIngredient12")
    val twelfthIngredient: String?,

    @SerialName("strIngredient13")
    val thirteenthIngredient: String?,

    @SerialName("strIngredient14")
    val fourteenthIngredient: String?,

    @SerialName("strIngredient15")
    val fifteenthIngredient: String?,

    @SerialName("strIngredient16")
    val sixteenthIngredient: String?,

    @SerialName("strIngredient17")
    val seventeenthIngredient: String?,

    @SerialName("strIngredient18")
    val eighteenthIngredient: String?,

    @SerialName("strIngredient19")
    val nineteenthIngredient: String?,

    @SerialName("strIngredient20")
    val twentiethIngredient: String?,

    @SerialName("strMeasure1")
    val firstMeasure: String?,

    @SerialName("strMeasure2")
    val secondMeasure: String?,

    @SerialName("strMeasure3")
    val thirdMeasure: String?,

    @SerialName("strMeasure4")
    val fourthMeasure: String?,

    @SerialName("strMeasure5")
    val fifthMeasure: String?,

    @SerialName("strMeasure6")
    val sixthMeasure: String?,

    @SerialName("strMeasure7")
    val seventhMeasure: String?,

    @SerialName("strMeasure8")
    val eighthMeasure: String?,

    @SerialName("strMeasure9")
    val ninthMeasure: String?,

    @SerialName("strMeasure10")
    val tenthMeasure: String?,

    @SerialName("strMeasure11")
    val eleventhMeasure: String?,

    @SerialName("strMeasure12")
    val twelfthMeasure: String?,

    @SerialName("strMeasure13")
    val thirteenthMeasure: String?,

    @SerialName("strMeasure14")
    val fourteenthMeasure: String?,

    @SerialName("strMeasure15")
    val fifteenthMeasure: String?,

    @SerialName("strMeasure16")
    val sixteenthMeasure: String?,

    @SerialName("strMeasure17")
    val seventeenthMeasure: String?,

    @SerialName("strMeasure18")
    val eighteenthMeasure: String?,

    @SerialName("strMeasure19")
    val nineteenthMeasure: String?,

    @SerialName("strMeasure20")
    val twentiethMeasure: String?,
)