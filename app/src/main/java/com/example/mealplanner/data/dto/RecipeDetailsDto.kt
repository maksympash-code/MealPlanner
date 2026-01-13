package com.example.mealplanner.data.dto

import com.google.gson.annotations.SerializedName

data class RecipeDetailsDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("image")
    val imageUrl: String?,

    @SerializedName("readyInMinutes")
    val readyMinutes: Int?,

    @SerializedName("servings")
    val servings: Int?,

    @SerializedName("instructions")
    val instructions: String?,

    @SerializedName("extendedIngredients")
    val ingredients: List<IngredientDto>?
)
