package com.example.mealplanner.data.dto

import com.google.gson.annotations.SerializedName

data class IngredientDto(
    @SerializedName("name")
    val name: String?,

    @SerializedName("amount")
    val amount: Double?,

    @SerializedName("unit")
    val unit: String?
)
