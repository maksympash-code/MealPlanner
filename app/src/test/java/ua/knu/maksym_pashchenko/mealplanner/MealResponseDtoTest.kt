package ua.knu.maksym_pashchenko.mealplanner

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import ua.knu.maksym_pashchenko.mealplanner.data.remote.dto.MealResponseDto

private val json = Json {
    ignoreUnknownKeys = true
}

class MealResponseDtoTest {

    @Test
    fun isMealResponseDto() {
        val text = javaClass.classLoader?.getResource("meal_response.json")?.readText()

        requireNotNull(text) { "Не вдалося знайти файл meal_response.json" }
        val response = json.decodeFromString<MealResponseDto>(text)

        val firstMeal = response.meals?.firstOrNull()

        assertEquals("52771", firstMeal?.id)
        assertEquals("Spicy Arrabiata Penne", firstMeal?.name)
    }
}