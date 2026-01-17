package com.example.mealplanner.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealplanner.presentation.screen.details.DetailsScreen
import com.example.mealplanner.presentation.screen.search.SearchScreen

@Composable
fun MealPlannerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {

        composable(Routes.SEARCH) {
            SearchScreen(
                onRecipeClick = { id ->
                    navController.navigate(Routes.details(id))
                }
            )
        }

        composable(
            route = Routes.DETAILS,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            DetailsScreen(
                recipeId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}