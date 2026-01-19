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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.data.local.MealPlannerDataBase
import com.example.mealplanner.data.remote.RetrofitServiceApiFactory
import com.example.mealplanner.data.repository.FavouritesRepositoryImpl
import com.example.mealplanner.data.repository.RecipesRepositoryImpl
import com.example.mealplanner.data.repository.ShoppingItemRepositoryImpl
import com.example.mealplanner.domain.models.RecipeDetails
import com.example.mealplanner.presentation.screen.details.components.DetailsTopBar
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    recipeId: Int,
    onBack: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var details by remember { mutableStateOf<RecipeDetails?>(null) }

    val api = remember { RetrofitServiceApiFactory.create() }
    val repo = remember { RecipesRepositoryImpl(api) }

    val context = LocalContext.current

    val db = remember { MealPlannerDataBase.getDatabase(context) }
    val favouritesRepo = remember { FavouritesRepositoryImpl(db.favouritesDao()) }

    val favourites by favouritesRepo.getFavourites().collectAsState(initial = emptyList())

    val isFavourite = favourites.any { it.id == recipeId }

    val shoppingRepo = remember { ShoppingItemRepositoryImpl(db) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(recipeId) {
        isLoading = true
        error = null
        details = null
        try {
            details = repo.getRecipeDetails(recipeId)
        } catch (e: Exception) {
            error = e.message ?: "Unknown error."
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { DetailsTopBar(
            onClick = onBack
        ) },

        floatingActionButton = {
            if (details != null) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            try {
                                shoppingRepo.addIngredients(details!!.ingredients)
                            } catch (e: Exception) {
                                error = e.message
                            }

                        }

                    }
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
                isLoading -> Text("Loading...", modifier = Modifier.padding(16.dp))

                error != null -> {
                    Text("Error: $error", modifier = Modifier.padding(16.dp))
                }

                details == null -> {
                    Text("No data", modifier = Modifier.padding(16.dp))
                }

                else -> {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = details!!.recipe.title,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (isFavourite) favouritesRepo.removeFromFavourites(recipeId)
                                    else favouritesRepo.addToFavourites(details!!.recipe)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isFavourite) "Remove" else "Add"
                            )
                        }

                    }


                    LazyColumn {
                        details?.ingredients?.let { ingredients ->
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


@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    MealPlannerTheme {
        DetailsScreen(
            recipeId = 1111,
            onBack = {}
        )
    }
}
