package ua.knu.maksym_pashchenko.mealplanner

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import ua.knu.maksym_pashchenko.mealplanner.data.mapper.toDomainRecipe
import ua.knu.maksym_pashchenko.mealplanner.data.remote.dto.MealResponseDto

private val json = Json {
    ignoreUnknownKeys = true
}

class MealMapperTest {

    @Test
    fun isFullRecipe() {
        val text = javaClass.classLoader?.getResource("meal_response.json")?.readText()

        requireNotNull(text) { "Не вдалося знайти файл meal_response.json" }

        val response = json.decodeFromString<MealResponseDto>(text)

        val meals = response.meals?.firstOrNull()

        requireNotNull(meals) { "Рецепт відсутній" }

        val domainMeal = meals.toDomainRecipe()

        assertEquals(8, domainMeal.ingredients.size)
        assertEquals("penne rigate", domainMeal.ingredients.first().name)
        assertEquals("1 pound", domainMeal.ingredients.first().measure)
    }

    @Test
    fun ignoresEmptyIngredients() {
        val text = javaClass.classLoader?.getResource("meal_response_with_one_ingredient.json")?.readText()

        requireNotNull(text) { "Не вдалося знайти файл meal_response_with_one_ingredient.json" }

        val response = json.decodeFromString<MealResponseDto>(text)

        val meals = response.meals?.firstOrNull()

        requireNotNull(meals) { "Рецепт відсутній" }

        val domainMeal = meals.toDomainRecipe()

        assertEquals(1, domainMeal.ingredients.size)
        assertEquals("Chicken", domainMeal.ingredients.first().name)
        assertEquals("500g", domainMeal.ingredients.first().measure)
    }

    @Test
    fun handlesMissingMeasure() {
        val text = javaClass.classLoader?.getResource("meal_response_with_zero_measures.json")?.readText()

        requireNotNull(text) { "Не вдалося знайти файл meal_response_with_zero_measures.json" }

        val response = json.decodeFromString<MealResponseDto>(text)

        val meals = response.meals?.firstOrNull()

        requireNotNull(meals) { "Рецепт відсутній" }

        val domainMeal = meals.toDomainRecipe()

        assertEquals(2, domainMeal.ingredients.size)
        assertEquals("Chicken", domainMeal.ingredients.first().name)
        assertEquals("", domainMeal.ingredients.first().measure)
        assertEquals("Salt", domainMeal.ingredients[1].name)
        assertEquals("", domainMeal.ingredients[1].measure)
    }
}