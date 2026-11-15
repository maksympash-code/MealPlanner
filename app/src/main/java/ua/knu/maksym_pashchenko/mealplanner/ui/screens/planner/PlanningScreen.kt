package ua.knu.maksym_pashchenko.mealplanner.ui.screens.planner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PlanningScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Meal planning",
        modifier = modifier.padding(16.dp)
    )
}