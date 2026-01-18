package com.example.mealplanner.data.local.favourites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_recipes")
data class FavouriteRecipeEntity(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,

    @ColumnInfo(name = "readyMinutes")
    val readyMinutes: Int?,

    @ColumnInfo(name = "servings")
    val servings: Int?
)