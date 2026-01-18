package com.example.mealplanner.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteRecipeEntity::class],
    version = 1
)
abstract class MealPlannerDataBase: RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MealPlannerDataBase? = null

        fun getDatabase(context: Context): MealPlannerDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }   //  if (INSTANCE != null) return@synchronized INSTANCE!!
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealPlannerDataBase::class.java,
                    "meal_planner_data_base"
                )
//                    .allowMainThreadQueries() // allows requests from DB in main thread.

                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}