package com.example.mealplanner.presentation.screen.search

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mealplanner.presentation.screen.search.model.BottomTab

@Composable
fun SearchRoute(
    onRecipeClick: (Int) -> Unit,
    onShoppingClick: () -> Unit
) {
    val vm: SearchViewModel = hiltViewModel()

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
            if (tab == BottomTab.SHOPPING) onShoppingClick()
            else vm.onTabSelected(tab)
        }
    )
}