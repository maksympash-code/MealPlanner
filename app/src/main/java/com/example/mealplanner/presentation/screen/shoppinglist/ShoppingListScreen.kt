package com.example.mealplanner.presentation.screen.shoppinglist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.mealplanner.data.local.MealPlannerDataBase
import com.example.mealplanner.data.repository.ShoppingItemRepositoryImpl
import com.example.mealplanner.presentation.screen.shoppinglist.components.ShoppingItemCard
import com.example.mealplanner.presentation.screen.shoppinglist.components.ShoppingListTopBar
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme
import kotlinx.coroutines.launch

@Composable
fun ShoppingListScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val db = remember { MealPlannerDataBase.getDatabase(context) }
    val repo = remember { ShoppingItemRepositoryImpl(db) }
    val items by repo.getShoppingList().collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { ShoppingListTopBar(onBack) },
        floatingActionButton = {
            FloatingActionButton(
                    onClick = {scope.launch {
                        repo.clearChecked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear all checked"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(
                items = items,
                key = { it.id }
            ) { ingredient ->
                ShoppingItemCard(
                    ingredient = ingredient,
                    onToggleChecked = {
                        scope.launch {
                            repo.toggleChecked(ingredient.id)
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview(){
    MealPlannerTheme {
        ShoppingListScreen(
            {}
        )
    }
}