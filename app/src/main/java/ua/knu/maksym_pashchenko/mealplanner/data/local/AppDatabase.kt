package ua.knu.maksym_pashchenko.mealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.knu.maksym_pashchenko.mealplanner.data.local.converter.DateConverter
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.FavoriteDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.IngredientDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.dao.RecipeDao
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.FavoriteEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.IngredientEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.MealPlanEntity
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.RecipeEntity

@Database(
    entities = [
        FavoriteEntity::class,
        RecipeEntity::class,
        IngredientEntity::class,
        MealPlanEntity::class,
    ],
    version = 2
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoriteDao() : FavoriteDao

    abstract fun recipeDao() : RecipeDao

    abstract fun ingredientDao() : IngredientDao
}