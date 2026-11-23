package ua.knu.maksym_pashchenko.mealplanner.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.knu.maksym_pashchenko.mealplanner.data.repository.FavoritesRepositoryImpl
import ua.knu.maksym_pashchenko.mealplanner.data.repository.RecipeRepositoryImpl
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.FavoritesRepository
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.RecipeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ) : RecipeRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ) : FavoritesRepository
}