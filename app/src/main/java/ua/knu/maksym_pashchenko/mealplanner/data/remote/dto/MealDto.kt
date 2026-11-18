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
    val category: String? = null,

    @SerialName("strArea")
    val area: String? = null,

    @SerialName("strInstructions")
    val instructions: String? = null,

    @SerialName("strMealThumb")
    val image: String? = null,

    @SerialName("strIngredient1")
    val firstIngredient: String? = null,

    @SerialName("strIngredient2")
    val secondIngredient: String? = null,

    @SerialName("strIngredient3")
    val thirdIngredient: String? = null,

    @SerialName("strIngredient4")
    val fourthIngredient: String? = null,

    @SerialName("strIngredient5")
    val fifthIngredient: String? = null,

    @SerialName("strIngredient6")
    val sixthIngredient: String? = null,

    @SerialName("strIngredient7")
    val seventhIngredient: String? = null,

    @SerialName("strIngredient8")
    val eighthIngredient: String? = null,

    @SerialName("strIngredient9")
    val ninthIngredient: String? = null,

    @SerialName("strIngredient10")
    val tenthIngredient: String? = null,

    @SerialName("strIngredient11")
    val eleventhIngredient: String? = null,

    @SerialName("strIngredient12")
    val twelfthIngredient: String? = null,

    @SerialName("strIngredient13")
    val thirteenthIngredient: String? = null,

    @SerialName("strIngredient14")
    val fourteenthIngredient: String? = null,

    @SerialName("strIngredient15")
    val fifteenthIngredient: String? = null,

    @SerialName("strIngredient16")
    val sixteenthIngredient: String? = null,

    @SerialName("strIngredient17")
    val seventeenthIngredient: String? = null,

    @SerialName("strIngredient18")
    val eighteenthIngredient: String? = null,

    @SerialName("strIngredient19")
    val nineteenthIngredient: String? = null,

    @SerialName("strIngredient20")
    val twentiethIngredient: String? = null,

    @SerialName("strMeasure1")
    val firstMeasure: String? = null,

    @SerialName("strMeasure2")
    val secondMeasure: String? = null,

    @SerialName("strMeasure3")
    val thirdMeasure: String? = null,

    @SerialName("strMeasure4")
    val fourthMeasure: String? = null,

    @SerialName("strMeasure5")
    val fifthMeasure: String? = null,

    @SerialName("strMeasure6")
    val sixthMeasure: String? = null,

    @SerialName("strMeasure7")
    val seventhMeasure: String? = null,

    @SerialName("strMeasure8")
    val eighthMeasure: String? = null,

    @SerialName("strMeasure9")
    val ninthMeasure: String? = null,

    @SerialName("strMeasure10")
    val tenthMeasure: String? = null,

    @SerialName("strMeasure11")
    val eleventhMeasure: String? = null,

    @SerialName("strMeasure12")
    val twelfthMeasure: String? = null,

    @SerialName("strMeasure13")
    val thirteenthMeasure: String? = null,

    @SerialName("strMeasure14")
    val fourteenthMeasure: String? = null,

    @SerialName("strMeasure15")
    val fifteenthMeasure: String? = null,

    @SerialName("strMeasure16")
    val sixteenthMeasure: String? = null,

    @SerialName("strMeasure17")
    val seventeenthMeasure: String? = null,

    @SerialName("strMeasure18")
    val eighteenthMeasure: String? = null,

    @SerialName("strMeasure19")
    val nineteenthMeasure: String? = null,

    @SerialName("strMeasure20")
    val twentiethMeasure: String? = null,
)