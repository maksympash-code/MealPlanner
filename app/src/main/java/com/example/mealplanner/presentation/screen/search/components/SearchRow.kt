package com.example.mealplanner.presentation.screen.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme

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

@Preview(showBackground = true)
@Composable
fun SearchRowPreview(){
    MealPlannerTheme {
        SearchRow()
    }
}