package com.example.mealplanner.presentation.screen.details

sealed interface DetailsUiEvent {
    data class ShowMessage(val text: String) : DetailsUiEvent
}