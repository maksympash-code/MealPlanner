package com.example.mealplanner.presentation.app

import android.content.Context
import com.example.mealplanner.data.local.MealPlannerDataBase
import com.example.mealplanner.data.remote.RetrofitServiceApiFactory
import com.example.mealplanner.data.repository.FavouritesRepositoryImpl
import com.example.mealplanner.data.repository.RecipesRepositoryImpl
import com.example.mealplanner.data.repository.ShoppingItemRepositoryImpl
import com.example.mealplanner.domain.repositories.FavouritesRepository
import com.example.mealplanner.domain.repositories.RecipesRepository
import com.example.mealplanner.domain.repositories.ShoppingRepository

class AppContainer(context: Context) {
    private val db = MealPlannerDataBase.getDatabase(context)
    private val api = RetrofitServiceApiFactory.create()

    val recipesRepo: RecipesRepository = RecipesRepositoryImpl(api)
    val favouritesRepo: FavouritesRepository = FavouritesRepositoryImpl(db.favouritesDao())
    val shoppingRepo: ShoppingRepository = ShoppingItemRepositoryImpl(db)
}
