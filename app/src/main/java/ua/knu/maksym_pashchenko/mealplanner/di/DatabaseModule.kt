package ua.knu.maksym_pashchenko.mealplanner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.knu.maksym_pashchenko.mealplanner.data.local.AppDatabase
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.FavoriteDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.IngredientDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.RecipeDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.migration.MIGRATION_1_2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = "mealplanner_database"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideRecipeDao(database: AppDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    fun provideIngredientDao(database: AppDatabase): IngredientDao {
        return database.ingredientDao()
    }
}