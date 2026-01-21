package com.example.mealplanner.presentation.app

object Routes {
    const val SEARCH = "search"
    const val DETAILS = "details/{id}"
    const val SHOPPING = "shopping"
    fun details(id: Int) = "details/${id}"
}