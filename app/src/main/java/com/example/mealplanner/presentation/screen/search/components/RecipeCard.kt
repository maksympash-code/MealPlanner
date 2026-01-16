package com.example.mealplanner.presentation.screen.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClickRecipe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        onClick = onClickRecipe,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = recipe.title,
                maxLines = 3,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(2f)
            )
            Text(
                text = "${recipe.id}",
                maxLines = 1,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    MealPlannerTheme {
        RecipeCard(
            Recipe(1111, "bread", "wdqwfq", null, null),
            {}
        )
    }
}