package com.example.mealplanner.presentation.screen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.data.local.MealPlannerDataBase
import com.example.mealplanner.data.remote.RetrofitServiceApiFactory
import com.example.mealplanner.data.repository.FavouritesRepositoryImpl
import com.example.mealplanner.data.repository.RecipesRepositoryImpl
import com.example.mealplanner.data.repository.ShoppingItemRepositoryImpl
import com.example.mealplanner.domain.models.Recipe
import com.example.mealplanner.presentation.screen.search.components.SearchBottomBar
import com.example.mealplanner.presentation.screen.search.components.RecipeCardList
import com.example.mealplanner.presentation.screen.search.components.SearchRow
import com.example.mealplanner.presentation.screen.search.components.SearchTopBar
import com.example.mealplanner.presentation.screen.search.model.BottomTab
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    state: SearchUiState,
    favourites: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    onShoppingClick: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    onTabSelected: (BottomTab) -> Unit
) {
    Scaffold(
        topBar = { SearchTopBar() },
        bottomBar = {
            SearchBottomBar(
                selectedTab = state.selectedTab,
                onTabSelected = { tab -> onTabSelected(tab)}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (state.selectedTab) {
                BottomTab.SEARCH -> {
                    SearchRow(
                        query = state.query,
                        onQueryChange = onQueryChanged,
                        onSearchClick = onSearchClick
                    )

                    if (state.isLoading) {
                        Text("Loading...", modifier = Modifier.padding(16.dp))
                    } else if (state.error != null) {
                        Text("Error: ${state.error}", modifier = Modifier.padding(16.dp))
                    }

                    RecipeCardList(
                        recipes = state.recipes,
                        onClickRecipe = onRecipeClick,
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }

                BottomTab.FAVOURITES -> {
                    if (favourites.isEmpty())
                        Text("No favourites yet", modifier = Modifier.padding(16.dp))
                    else {
                        RecipeCardList(
                            recipes = favourites,
                            onClickRecipe = onRecipeClick,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                }

                BottomTab.SHOPPING -> {}
            }
        }
    }
}


@Preview
@Composable
fun SearchScreenPreview(){
    MealPlannerTheme {
        SearchScreen(
            SearchUiState(),
            emptyList(),
            {},
            {},
            {},
            {},
            {}
        )
    }
}