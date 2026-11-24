package ua.knu.maksym_pashchenko.mealplanner.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ua.knu.maksym_pashchenko.mealplanner.R
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe

@Composable
fun RecipeCard(
    recipe: Recipe,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onRecipeClick(recipe.id) }
    ) {
        Column{
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                placeholder = painterResource(R.drawable.empty_photo_backdrop),
                error = painterResource(R.drawable.empty_photo_backdrop),
                fallback = painterResource(R.drawable.empty_photo_backdrop)
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = recipe.category ?: "Unknown category",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = recipe.area ?: "Unknown area",
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}