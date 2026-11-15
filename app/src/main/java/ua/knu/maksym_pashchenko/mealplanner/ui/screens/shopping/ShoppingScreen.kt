package ua.knu.maksym_pashchenko.mealplanner.ui.screens.shopping

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Shopping list",
        modifier = modifier.padding(16.dp)
    )
}