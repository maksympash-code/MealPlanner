package com.example.mealplanner.presentation.screen.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme

@Composable
fun RecipeCardList(
    recipes: List<Recipe>,
    onClickRecipe: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recipes.size) {
            val recipe = recipes[it]
            RecipeCard(
                recipe = recipe,
                onClickRecipe = { onClickRecipe(recipe.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardListPreview() {
    MealPlannerTheme {
        RecipeCardList(
            listOf(
                Recipe(1111, "bread", "wdqwfq", null, null),
                Recipe(1112, "milk", "wdqwfq", null, null)
            ),
            {}
        )
    }
}