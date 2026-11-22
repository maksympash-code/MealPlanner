package ua.knu.maksym_pashchenko.mealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.FavoriteDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.IngredientDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.RecipeDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.FavoriteEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.IngredientEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.RecipeEntity

@Database(
    entities = [
        FavoriteEntity::class,
        RecipeEntity::class,
        IngredientEntity::class,
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao() : FavoriteDao

    abstract fun recipeDao() : RecipeDao

    abstract fun ingredientDao() : IngredientDao
}