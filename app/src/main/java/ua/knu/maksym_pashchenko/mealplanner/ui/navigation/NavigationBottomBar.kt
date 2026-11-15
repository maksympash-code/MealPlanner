package ua.knu.maksym_pashchenko.mealplanner.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NavigationBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    BottomAppBar {
        NavigationBarItem(
            selected = currentRoute == Routes.RECIPES,
            onClick = { onNavigate(Routes.RECIPES) },
            icon = {
                Icon(
                    imageVector = Icons.Sharp.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "Home"
                )
            }
        )

        NavigationBarItem(
            selected =  currentRoute == Routes.FAVORITES,
            onClick = { onNavigate(Routes.FAVORITES) },
            icon = {
                Icon(
                    imageVector = if (currentRoute == Routes.FAVORITES) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "Favorites"
                )
            }
        )

        NavigationBarItem(
            selected =  currentRoute == Routes.PLANNING,
            onClick = { onNavigate(Routes.PLANNING) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "Planner"
                )
            }
        )

        NavigationBarItem(
            selected =  currentRoute == Routes.SHOPPING,
            onClick = { onNavigate(Routes.SHOPPING) },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "Shopping"
                )
            }
        )
    }
}