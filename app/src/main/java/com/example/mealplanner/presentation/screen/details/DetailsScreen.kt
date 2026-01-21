package com.example.mealplanner.presentation.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mealplanner.presentation.screen.details.components.DetailsTopBar

@Composable
fun DetailsScreen(
    recipeId: Int,
    onBack: () -> Unit,
    viewModel: DetailsViewModel
) {

    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(recipeId) { viewModel.load(recipeId) }

    Scaffold(
        topBar = { DetailsTopBar(
            onClick = onBack
        ) },

        floatingActionButton = {
            if (state.details != null) {
                FloatingActionButton(
                    onClick = viewModel::onAddIngredientsClick
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add ingredients"
                    )
                }
            }

        }
    ) {
        Column(modifier = Modifier.padding(it)) {

            when {
                state.isLoading -> Text("Loading...", modifier = Modifier.padding(16.dp))

                state.error != null -> {
                    Text("Error: ${state.error}", modifier = Modifier.padding(16.dp))
                }

                state.details == null -> {
                    Text("No data", modifier = Modifier.padding(16.dp))
                }

                else -> {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = state.details!!.recipe.title,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = viewModel::onToggleFavouriteClick
                        ) {
                            Icon(
                                imageVector = if (state.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (state.isFavourite) "Remove" else "Add"
                            )
                        }

                    }


                    LazyColumn {
                        state.details?.ingredients?.let { ingredients ->
                            items(
                                ingredients.size,
                            ) {index ->
                                val ingredient = ingredients[index]

                                Column {
                                    Row {
                                        Text(
                                            text = ingredient.name,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "${ingredient.amount}",
                                            modifier = Modifier.weight(0.5f)
                                        )
                                        Text(
                                            text = ingredient.unit ?: "",
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DetailsScreenPreview() {
//    MealPlannerTheme {
//        DetailsScreen(
//            recipeId = 1111,
//            onBack = {},
//        )
//    }
//}
