package com.example.mealplanner.presentation.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mealplanner.presentation.screen.theme.ui.MealPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealPlannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealPlannerTheme {
                MealPlannerApp()
            }
        }
    }
}