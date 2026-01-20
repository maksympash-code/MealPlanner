package com.example.mealplanner.presentation.screen.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mealplanner.data.local.MealPlannerDataBase
import com.example.mealplanner.data.remote.RetrofitServiceApiFactory
import com.example.mealplanner.data.repository.FavouritesRepositoryImpl
import com.example.mealplanner.data.repository.RecipesRepositoryImpl
import com.example.mealplanner.presentation.screen.search.model.BottomTab

@Composable
fun SearchRoute(
    onRecipeClick: (Int) -> Unit,
    onShoppingClick: () -> Unit
) {
    val context = LocalContext.current

    val api = remember { RetrofitServiceApiFactory.create() }
    val recipesRepo = remember { RecipesRepositoryImpl(api) }

    val db = remember { MealPlannerDataBase.getDatabase(context) }
    val favouritesRepo = remember { FavouritesRepositoryImpl(db.favouritesDao()) }

    val factory = remember { SearchViewModelFactory(recipesRepo, favouritesRepo) }
    val vm: SearchViewModel = viewModel(factory = factory)

    val state = vm.uiState.collectAsStateWithLifecycle().value
    val favourites = vm.favourites.collectAsStateWithLifecycle().value

    SearchScreen(
        state = state,
        favourites = favourites,
        onRecipeClick = onRecipeClick,
        onShoppingClick = onShoppingClick,
        onQueryChanged = vm::onQueryChanged,
        onSearchClick = vm::onSearchClick,
        onTabSelected = { tab ->
            if (tab == BottomTab.SHOPPING) onShoppingClick
            else vm.onTabSelected(tab)
        }
    )
}