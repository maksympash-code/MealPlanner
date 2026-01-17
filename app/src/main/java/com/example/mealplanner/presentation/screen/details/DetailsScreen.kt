package com.example.mealplanner.presentation.screen.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsScreen(
    recipeId: Int,
    onBack: () -> Unit
) {
    Text("Details for id: $recipeId")
}