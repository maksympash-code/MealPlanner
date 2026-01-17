package com.example.mealplanner.presentation.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mealplanner.data.remote.RetrofitServiceApiFactory
import com.example.mealplanner.data.repository.RecipesRepositoryImpl
import com.example.mealplanner.domain.models.RecipeDetails
import com.example.mealplanner.presentation.screen.search.components.SearchTopBar

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
        topBar = { SearchTopBar() }
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
                    Text(text = details!!.recipe.title)

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