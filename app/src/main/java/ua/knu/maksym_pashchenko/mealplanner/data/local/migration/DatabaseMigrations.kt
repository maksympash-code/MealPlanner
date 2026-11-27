package ua.knu.maksym_pashchenko.mealplanner.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE `meal_plans` (`date` INTEGER NOT NULL, `mealType` TEXT NOT NULL, `recipeId` TEXT NOT NULL, " + "PRIMARY KEY(`date`, `mealType`))")
    }
}

