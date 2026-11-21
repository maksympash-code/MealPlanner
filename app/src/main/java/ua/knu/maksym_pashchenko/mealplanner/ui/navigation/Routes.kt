package ua.knu.maksym_pashchenko.mealplanner.ui.navigation

object Routes {
    const val RECIPES = "recipes"
    const val FAVORITES = "favorites"
    const val PLANNING = "planning"
    const val SHOPPING = "shopping"

    const val RECIPE_DETAILS = "recipe/{recipeId}"

    fun recipeDetails(recipeId: String): String {
        return "recipe/$recipeId"
    }
}