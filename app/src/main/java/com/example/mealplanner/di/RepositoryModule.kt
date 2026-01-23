package com.example.mealplanner.di

import com.example.mealplanner.data.repository.FavouritesRepositoryImpl
import com.example.mealplanner.data.repository.RecipesRepositoryImpl
import com.example.mealplanner.data.repository.ShoppingItemRepositoryImpl
import com.example.mealplanner.domain.repositories.FavouritesRepository
import com.example.mealplanner.domain.repositories.RecipesRepository
import com.example.mealplanner.domain.repositories.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRecipesRepo(impl: RecipesRepositoryImpl): RecipesRepository

    @Binds
    abstract fun bindFavouritesRepo(impl: FavouritesRepositoryImpl): FavouritesRepository

    @Binds
    abstract fun bindShoppingRepo(impl: ShoppingItemRepositoryImpl): ShoppingRepository
}