package com.example.mealplanner.presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealplanner.presentation.screen.details.DetailsScreen
import com.example.mealplanner.presentation.screen.details.DetailsViewModel
import com.example.mealplanner.presentation.screen.search.SearchRoute
import com.example.mealplanner.presentation.screen.shoppinglist.ShoppingListScreen
import com.example.mealplanner.presentation.screen.shoppinglist.ShoppingViewModel

@Composable
fun MealPlannerApp() {
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
                onShoppingClick = {
                    navController.navigate(Routes.SHOPPING) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Routes.DETAILS,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val vm: DetailsViewModel = hiltViewModel(backStackEntry)

            DetailsScreen(
                recipeId = id,
                onBack = { navController.popBackStack() },
                viewModel = vm
            )
        }

        composable(
            route = Routes.SHOPPING
        ) {
            val vm: ShoppingViewModel = hiltViewModel()

            val state by vm.uiState.collectAsState()

            ShoppingListScreen(
                onBack = { navController.popBackStack() },
                state = state,
                onClearCheckedClick = vm::onClearCheckedClick,
                onToggleCheckedClick = vm::onToggleCheckedClick
            )
        }
    }
}