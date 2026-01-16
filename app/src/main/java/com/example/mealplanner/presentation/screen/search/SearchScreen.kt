package com.example.mealplanner.presentation.screen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.data.remote.RetrofitServiceApiFactory
import com.example.mealplanner.data.repository.RecipesRepositoryImpl
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.presentation.screen.search.components.SearchBottomBar
import com.example.mealplanner.presentation.screen.search.components.RecipeCardList
import com.example.mealplanner.presentation.screen.search.components.SearchRow
import com.example.mealplanner.presentation.screen.search.components.SearchTopBar
import com.example.mealplanner.presentation.screen.search.model.BottomTab
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchScreen() {
    var selectedTab by rememberSaveable { mutableStateOf(BottomTab.SEARCH) }
    var query by rememberSaveable { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var recipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }

    val api = remember { RetrofitServiceApiFactory.create() }
    val repo = remember { RecipesRepositoryImpl(api) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { SearchTopBar() },
        bottomBar = {
            SearchBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (selectedTab) {
                BottomTab.SEARCH -> {
                    SearchRow(
                        query = query,
                        onQueryChange = { query = it },
                        onSearchClick = {
                            scope.launch {
                                isLoading = true
                                error = null
                                try {
                                    recipes = repo.searchRecipes(query.trim())
                                } catch (e: Exception) {
                                    error = e.message ?: "Unknown error"
                                } finally {
                                    isLoading = false
                                }
                            }

                        }
                    )
                    RecipeCardList(
                        recipes = recipes,
                        onClickRecipe = {},
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }

                BottomTab.FAVOURITES -> Text(
                    "Favourites screen (stub)",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun SearchScreenPreview(){
    MealPlannerTheme {
        SearchScreen()
    }
}