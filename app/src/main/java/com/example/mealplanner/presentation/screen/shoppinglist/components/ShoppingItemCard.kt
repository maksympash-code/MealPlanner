package com.example.mealplanner.presentation.screen.shoppinglist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mealplanner.domain.models.ShoppingItem
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme

@Composable
fun ShoppingItemCard(
    ingredient: ShoppingItem,
    onToggleChecked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = ingredient.name
                )
                val unitText = ingredient.unit?.takeIf { it.isNotBlank() } ?: ""
                Text(
                    text = "${ingredient.amount} $unitText".trim()
                )
            }

            Checkbox(
                checked = ingredient.checked,
                onCheckedChange = { onToggleChecked() },
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingItemCardPreview() {
    MealPlannerTheme {
        ShoppingItemCard(
            ingredient = ShoppingItem(
                name = "Carrot",
                amount = 1.0,
                unit = "sht",
                checked = false,
                id = 1111
            ),
            onToggleChecked = {}
        )
    }
}