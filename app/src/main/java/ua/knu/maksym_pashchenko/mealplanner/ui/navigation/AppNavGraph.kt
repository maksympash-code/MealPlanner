package ua.knu.maksym_pashchenko.mealplanner.ui.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.details.RecipeDetailsScreen
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.favourites.FavoritesScreen
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.planner.PlanningScreen
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes.RecipesScreen
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.shopping.ShoppingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute in setOf(
        Routes.RECIPES,
        Routes.FAVORITES,
        Routes.PLANNING,
        Routes.SHOPPING
    )

    fun navigateSingleTopTo(route: String) =
        navController.navigate(route) {
            popUpTo(
                navController.graph.findStartDestination().id
            ) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meal Planner"
                    )
                }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = ::navigateSingleTopTo
                )
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.RECIPES,
            modifier = modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable(route = Routes.RECIPES) {
                RecipesScreen(
                    onRecipeClick = { recipeId ->
                        navController.navigate(
                            Routes.recipeDetails(recipeId = recipeId)
                        )
                    }
                )
            }
            composable(route = Routes.FAVORITES) {
                FavoritesScreen()
            }
            composable(route = Routes.PLANNING) {
                PlanningScreen()
            }
            composable(route = Routes.SHOPPING) {
                ShoppingScreen()
            }
            composable(
                route = Routes.RECIPE_DETAILS,
                arguments = listOf(
                    navArgument("recipeId") {
                        type = NavType.StringType
                    }
                )
            ) {
                RecipeDetailsScreen()
            }
        }
    }
}