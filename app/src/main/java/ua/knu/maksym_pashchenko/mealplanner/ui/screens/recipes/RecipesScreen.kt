package ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Search and view recipes",
        modifier = modifier.padding(16.dp)
    )
}