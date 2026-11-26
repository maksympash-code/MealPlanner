package ua.knu.maksym_pashchenko.mealplanner.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ua.knu.maksym_pashchenko.mealplanner.R
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Ingredient
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.components.CenteredStateContent
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.details.state.RecipeDetailsUiState
import ua.knu.maksym_pashchenko.mealplanner.ui.theme.MealPlannerTheme

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<RecipeDetailsViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()

    val isUpdatingFavorite by viewModel.isUpdatingFavorite.collectAsStateWithLifecycle()

    val favoriteError by viewModel.favoriteError.collectAsStateWithLifecycle()

    val favoriteReadError by viewModel.favoriteReadError.collectAsStateWithLifecycle()

    RecipeDetailsScreenContent(
        uiState = uiState,
        isFavorite = isFavorite,
        isUpdatingFavorite = isUpdatingFavorite,
        favoriteError = favoriteError,
        canRetryFavoriteStatus = favoriteReadError != null && isFavorite == null && !isUpdatingFavorite,
        onToggleFavorite = viewModel::toggleFavorite,
        onRetryFavoriteStatus = viewModel::retryFavoriteStatus,
        modifier = modifier
    )


}

@Composable
private fun RecipeDetailsScreenContent(
    uiState: RecipeDetailsUiState,
    isFavorite: Boolean?,
    isUpdatingFavorite: Boolean,
    favoriteError: String?,
    canRetryFavoriteStatus: Boolean,
    onToggleFavorite: () -> Unit,
    onRetryFavoriteStatus: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val result = uiState) {
            RecipeDetailsUiState.Loading -> {
                CenteredStateContent(
                    modifier = Modifier.weight(1f)
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecipeDetailsUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            AsyncImage(
                                model = result.recipe.imageUrl,
                                contentDescription = result.recipe.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                placeholder = painterResource(R.drawable.empty_photo_backdrop),
                                error = painterResource(R.drawable.empty_photo_backdrop),
                                fallback = painterResource(R.drawable.empty_photo_backdrop)
                            )

                            Text(
                                text = result.recipe.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = result.recipe.area ?: "Unknown area",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = result.recipe.category ?: "Unknown category",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            IconButton(
                                onClick = onToggleFavorite,
                                enabled = !isUpdatingFavorite && isFavorite != null,
                            ) {
                                Icon(
                                    imageVector = when (isFavorite) {
                                        true -> Icons.Default.Favorite
                                        false -> Icons.Default.FavoriteBorder
                                        else -> Icons.Default.Refresh
                                    },
                                    contentDescription = when (isFavorite) {
                                        true -> "Remove from favorites"
                                        false -> "Add to favorites"
                                        else -> "Favorite status unavailable"
                                    },
                                )
                            }

                            favoriteError?.let { error ->
                                Text(
                                    text = error,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            if (canRetryFavoriteStatus) {
                                OutlinedButton(
                                    onClick = onRetryFavoriteStatus,
                                    modifier = Modifier.width(130.dp)
                                ) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }

                    items(
                        items = result.recipe.ingredients
                    ) { ingredient ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = ingredient.name,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = ingredient.measure,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    item {
                        Text(
                            text = result.recipe.instructions ?: "Unknown instruction",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            is RecipeDetailsUiState.Error -> {
                CenteredStateContent(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = result.message, color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

private fun previewRecipe(): Recipe = Recipe(
    id = "preview-001",
    name = "Test Chicken",
    category = "Chicken",
    area = "Ukraine",
    instructions = 	"Cook the chicken and add salt.",
    imageUrl = null,
    ingredients = listOf(
        Ingredient(
            name = "Chicken",
            measure = "500 g"
        ),
        Ingredient(
            name = "Salt",
            measure = "1 tsp"
        )
    )
)


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
private fun LoadingPreview() {
    MealPlannerTheme {
        RecipeDetailsScreenContent(
            uiState = RecipeDetailsUiState.Loading,
            isFavorite = null,
            isUpdatingFavorite = false,
            favoriteError = null,
            canRetryFavoriteStatus = false,
            onToggleFavorite = { },
            onRetryFavoriteStatus = {},
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
private fun ErrorPreview() {
    MealPlannerTheme {
        RecipeDetailsScreenContent(
            uiState = RecipeDetailsUiState.Error("Recipe not found"),
            isFavorite = null,
            isUpdatingFavorite = false,
            favoriteError = null,
            canRetryFavoriteStatus = false,
            onToggleFavorite = { },
            onRetryFavoriteStatus = {},
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
private fun SuccessPreview() {
    MealPlannerTheme {
        RecipeDetailsScreenContent(
            uiState = RecipeDetailsUiState.Success(previewRecipe()),
            isFavorite = false,
            isUpdatingFavorite = false,
            favoriteError = null,
            canRetryFavoriteStatus = false,
            onToggleFavorite = { },
            onRetryFavoriteStatus = {},
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
private fun FavoritePreview() {
    MealPlannerTheme {
        RecipeDetailsScreenContent(
            uiState = RecipeDetailsUiState.Success(previewRecipe()),
            isFavorite = true,
            isUpdatingFavorite = false,
            favoriteError = null,
            canRetryFavoriteStatus = false,
            onToggleFavorite = { },
            onRetryFavoriteStatus = {},
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
private fun FavoriteReadErrorPreview() {
    MealPlannerTheme {
        RecipeDetailsScreenContent(
            uiState = RecipeDetailsUiState.Success(previewRecipe()),
            isFavorite = null,
            isUpdatingFavorite = false,
            favoriteError = "Database read failed",
            canRetryFavoriteStatus = true,
            onToggleFavorite = { },
            onRetryFavoriteStatus = {},
        )
    }
}