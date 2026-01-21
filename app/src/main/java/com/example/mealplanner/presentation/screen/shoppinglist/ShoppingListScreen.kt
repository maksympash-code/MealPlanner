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
import androidx.compose.ui.unit.dp
import com.example.mealplanner.data.local.MealPlannerDataBase
import com.example.mealplanner.data.repository.ShoppingItemRepositoryImpl
import com.example.mealplanner.presentation.screen.shoppinglist.components.ShoppingItemCard
import com.example.mealplanner.presentation.screen.shoppinglist.components.ShoppingListTopBar
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme
import kotlinx.coroutines.launch

@Composable
fun ShoppingListScreen(
    onBack: () -> Unit,
    state: ShoppingUiState,
    onClearCheckedClick: () -> Unit,
    onToggleCheckedClick: (Int) -> Unit
) {

    Scaffold(
        topBar = { ShoppingListTopBar(onBack) },
        floatingActionButton = {
            FloatingActionButton(onClick = onClearCheckedClick) {
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
                items = state.items,
                key = { it.id }
            ) { ingredient ->
                ShoppingItemCard(
                    ingredient = ingredient,
                    onToggleChecked = { onToggleCheckedClick(ingredient.id) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ShoppingListScreenPreview(){
//    MealPlannerTheme {
//        ShoppingListScreen(
//            {}
//        )
//    }
//}