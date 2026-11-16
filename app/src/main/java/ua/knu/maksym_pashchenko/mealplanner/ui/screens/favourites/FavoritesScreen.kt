package ua.knu.maksym_pashchenko.mealplanner.ui.screens.favourites

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Saved recipes",
        modifier = modifier.padding(16.dp)
    )
}