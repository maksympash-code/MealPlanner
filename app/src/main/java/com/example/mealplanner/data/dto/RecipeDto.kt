package com.example.mealplanner.data.dto

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("image")
    val imageUrl: String?,
)
