package com.example.mealplanner.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme

class MealPlannerActivity : ComponentActivity() {
    val container by lazy { AppContainer(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealPlannerTheme {
                MealPlannerApp(container)
            }
        }
    }
}