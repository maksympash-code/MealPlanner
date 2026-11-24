package ua.knu.maksym_pashchenko.mealplanner.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.components.CenteredStateContent
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.components.RecipeCard
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.favorites.state.FavoritesUiState

@Composable
fun FavoritesScreen(
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel<FavoritesViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreenContent(
        uiState = uiState,
        onRecipeClick = onRecipeClick,
        modifier = modifier
    )
}

@Composable
private fun FavoritesScreenContent(
    uiState: FavoritesUiState,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            FavoritesUiState.Loading -> {
                CenteredStateContent(
                    modifier = Modifier.weight(1f)
                ) {
                    CircularProgressIndicator()
                }
            }

            FavoritesUiState.Empty -> {
                CenteredStateContent(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "No favorite recipes yet",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            is FavoritesUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = uiState.favorites,
                        key = { recipe -> recipe.id }
                    ) {recipe ->
                        RecipeCard(
                            recipe = recipe,
                            onRecipeClick = onRecipeClick
                        )
                    }
                }
            }

            is FavoritesUiState.Error -> {
                CenteredStateContent(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

}