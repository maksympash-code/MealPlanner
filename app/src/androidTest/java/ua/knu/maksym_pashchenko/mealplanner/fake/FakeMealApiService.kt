
package ua.knu.maksym_pashchenko.mealplanner.fake

import ua.knu.maksym_pashchenko.mealplanner.data.remote.MealApiService
import ua.knu.maksym_pashchenko.mealplanner.data.remote.dto.MealResponseDto

class FakeMealApiService : MealApiService {

    var lookupCallCount: Int = 0
        private set

    var lastLookupId: String? = null
        private set

    var lookupResponse: MealResponseDto =
        MealResponseDto(meals = null)

    var lookupException: Exception? = null

    override suspend fun getMealById(id: String): MealResponseDto {
        lookupCallCount++
        lastLookupId = id

        lookupException?.let { exception ->
            throw exception
        }

        return lookupResponse
    }

    override suspend fun searchMealsByName(
        name: String
    ): MealResponseDto {
        error("Unexpected searchMealsByName() call")
    }

    override suspend fun getRandomMeal(): MealResponseDto {
        error("Unexpected getRandomMeal() call")
    }
}
