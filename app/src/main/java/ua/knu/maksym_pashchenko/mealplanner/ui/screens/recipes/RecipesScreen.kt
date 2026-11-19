package ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes.components.RecipeCard
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes.state.RecipesResultState

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<RecipesViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.query,
            onValueChange = { newQuery ->
                viewModel.onQueryChange(newQuery)
            },
            label = {
                Text(text = "Search recipes")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val result = uiState.resultState) {
            RecipesResultState.Initial -> {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Try to find recipes"
                    )
                }
            }

            RecipesResultState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecipesResultState.Success  -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = result.recipes,
                            key = { recipe -> recipe.id }
                        ) { recipe ->
                            RecipeCard(
                                recipe = recipe
                            )
                        }
                    }
                }

            RecipesResultState.Empty ->  {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nothing found"
                    )
                }
            }

            is RecipesResultState.Error ->  {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = result.message,
                            color = MaterialTheme.colorScheme.error
                        )

                        Button(
                            onClick = {
                                viewModel.retrySearch()
                            }
                        ) {
                            Text(
                                text = "Retry"
                            )
                        }
                    }

                }
            }
        }
    }
}