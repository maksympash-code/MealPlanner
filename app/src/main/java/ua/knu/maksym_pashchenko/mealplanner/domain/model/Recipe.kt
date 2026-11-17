package ua.knu.maksym_pashchenko.mealplanner.domain.model

data class Recipe(
    val id: String,
    val name: String,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val imageUrl: String?,
    val ingredients: List<Ingredient>
)
