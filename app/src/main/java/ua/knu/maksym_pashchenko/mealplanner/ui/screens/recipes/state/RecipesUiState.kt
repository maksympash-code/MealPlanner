package ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes.state

data class RecipesUiState(
    val query: String = "",
    val retryCount: Int = 0,
    val resultState: RecipesResultState = RecipesResultState.Initial
)
