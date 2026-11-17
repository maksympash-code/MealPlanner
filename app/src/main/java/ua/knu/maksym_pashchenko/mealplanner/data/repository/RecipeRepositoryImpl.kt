package ua.knu.maksym_pashchenko.mealplanner.data.repository

import ua.knu.maksym_pashchenko.mealplanner.data.mapper.toDomainRecipe
import ua.knu.maksym_pashchenko.mealplanner.data.remote.MealApiService
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: MealApiService
) : RecipeRepository {
    override suspend fun searchRecipes(query: String): List<Recipe> {
        return api.searchMealsByName(name = query)
            .meals
            ?.map { mealDto ->
                mealDto.toDomainRecipe()
            }
            .orEmpty()
    }

    override suspend fun getRecipeById(id: String): Recipe? {
        return api.getMealById(id = id)
            .meals
            ?.firstOrNull()
            ?.toDomainRecipe()
    }

    override suspend fun getRandomRecipe(): Recipe? {
        return api.getRandomMeal()
            .meals
            ?.firstOrNull()
            ?.toDomainRecipe()
    }
}