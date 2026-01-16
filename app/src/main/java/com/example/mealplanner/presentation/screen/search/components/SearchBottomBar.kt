package com.example.mealplanner.presentation.screen.search.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mealplanner.presentation.screen.search.model.BottomTab
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme

@Composable
fun SearchBottomBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        NavigationBarItem(
            selected = selectedTab == BottomTab.SEARCH,
            onClick = { onTabSelected(BottomTab.SEARCH) },
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text("Search") }
        )

        NavigationBarItem(
            selected = selectedTab == BottomTab.FAVOURITES,
            onClick = { onTabSelected(BottomTab.FAVOURITES) },
            icon = { Icon(Icons.Filled.FavoriteBorder, contentDescription = "Favourite") },
            label = { Text("Favourite") }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarPreview() {
    MealPlannerTheme {
        var selectedTab by remember { mutableStateOf(BottomTab.SEARCH) }

        SearchBottomBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it}
        )
    }
}