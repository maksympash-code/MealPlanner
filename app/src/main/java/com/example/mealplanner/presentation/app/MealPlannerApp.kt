package com.example.mealplanner.presentation.app

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealplanner.presentation.screen.details.DetailsScreen
import com.example.mealplanner.presentation.screen.details.DetailsViewModel
import com.example.mealplanner.presentation.screen.details.DetailsViewModelFactory
import com.example.mealplanner.presentation.screen.search.SearchRoute
import com.example.mealplanner.presentation.screen.shoppinglist.ShoppingListScreen

@Composable
fun MealPlannerApp(container: AppContainer) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {

        composable(Routes.SEARCH) {
            SearchRoute (
                onRecipeClick = { id ->
                    navController.navigate(Routes.details(id))
                },
                onShoppingClick = { navController.navigate(Routes.SHOPPING) }
            )
        }

        composable(
            route = Routes.DETAILS,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val vm: DetailsViewModel = viewModel(
                factory = DetailsViewModelFactory(
                    recipesRepo = container.recipesRepo,
                    favouritesRepo = container.favouritesRepo,
                    shoppingRepo =  container.shoppingRepo
                )
            )

            DetailsScreen(
                recipeId = id,
                onBack = { navController.popBackStack() },
                viewModel = vm
            )
        }

        composable(
            route = Routes.SHOPPING
        ) {
            ShoppingListScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}