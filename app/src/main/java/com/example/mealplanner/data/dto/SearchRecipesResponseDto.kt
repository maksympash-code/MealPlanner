package com.example.mealplanner.data.dto

import com.google.gson.annotations.SerializedName

data class SearchRecipesResponseDto(
    @SerializedName("results")
    val recipes: List<RecipeDto>?,

    @SerializedName("offset")
    val offset: Int?,

    @SerializedName("number")
    val number: Int?,

    @SerializedName("totalResults")
    val totalResults: Int?
)
