package com.example.mealplanner.data.local.favourites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Query("SELECT * FROM favourite_recipes")
    fun observeFavourites(): Flow<List<FavouriteRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: FavouriteRecipeEntity)

    @Query("DELETE FROM favourite_recipes WHERE id = :favouriteId")
    suspend fun removeFavourite(favouriteId: Int)
}