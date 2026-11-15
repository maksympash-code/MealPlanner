package ua.knu.maksym_pashchenko.mealplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ua.knu.maksym_pashchenko.mealplanner.ui.navigation.AppNavGraph
import ua.knu.maksym_pashchenko.mealplanner.ui.theme.MealPlannerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealPlannerTheme {
                AppNavGraph()
            }
        }
    }
}

