package com.example.mealplanner.presentation.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme


class SearchScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealPlannerTheme {
                var selectedTab by remember { mutableStateOf(BottomTab.SEARCH) }
                Scaffold(
                    topBar = { SmallTopAppBar() },
                    bottomBar = {
                        SmallBottomAppBar(
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
                                SearchRow()
                                RecipeCardList(
                                    Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                )
                            }
                            BottomTab.FAVOURITES -> Text("Favourites screen (stub)", modifier = Modifier.padding(16.dp))
                        }

                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        title = {
            Text(
                "Meal Planner",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
                },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
                  },
        )
}


@Composable
fun SearchRow(modifier: Modifier = Modifier){
    var query by remember { mutableStateOf("") }

    Surface(modifier = modifier) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                label = { Text("Write a dish") },
                value = query,
                onValueChange = { query = it},
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth()
            )

            Button(
                onClick = { },
            ) {
                Text("Search")
            }
        }

    }
}

@Composable
fun RecipeCard(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
    ) {
        Text(
            text = "Some Text",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RecipeCardList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(5) {
            RecipeCard(modifier = Modifier.fillMaxWidth())
        }
    }
}


enum class BottomTab { SEARCH, FAVOURITES }
@Composable
fun SmallBottomAppBar(
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


@Preview
@Composable
fun SearchScreenPreview(){
    MealPlannerTheme {
        var selectedTab by remember { mutableStateOf(BottomTab.SEARCH) }

        Scaffold(
            topBar = { SmallTopAppBar() },
            bottomBar = {
                SmallBottomAppBar(
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
                        SearchRow()
                        RecipeCardList(
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                    BottomTab.FAVOURITES -> Text("Favourites screen (stub)", modifier = Modifier.padding(16.dp))
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmallTopAppBarPreview() {
    MealPlannerTheme {
        SmallTopAppBar()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchRowPreview(){
    MealPlannerTheme {
        SearchRow()
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    MealPlannerTheme {
        RecipeCard()
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardListPreview() {
    MealPlannerTheme {
        RecipeCardList()
    }
}

@Preview(showBackground = true)
@Composable
fun SmallBottomAppBarPreview() {
    MealPlannerTheme {
        var selectedTab by remember { mutableStateOf(BottomTab.SEARCH) }

        SmallBottomAppBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it}
        )
    }
}