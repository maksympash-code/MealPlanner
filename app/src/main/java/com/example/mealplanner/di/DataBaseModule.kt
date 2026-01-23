package com.example.mealplanner.di

import android.content.Context
import androidx.room.Room
import com.example.mealplanner.data.local.MealPlannerDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): MealPlannerDataBase =
        Room.databaseBuilder(
            context,
            MealPlannerDataBase::class.java,
            "meal_planner_data_base"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesFavouritesDao(db: MealPlannerDataBase) = db.favouritesDao()

    @Provides
    fun providesShopItemDao(db: MealPlannerDataBase) = db.shopItemDao()
}