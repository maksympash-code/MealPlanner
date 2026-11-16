package ua.knu.maksym_pashchenko.mealplanner.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ua.knu.maksym_pashchenko.mealplanner.data.remote.dto.MealResponseDto

interface MealApiService {
    @GET("search.php")
    suspend fun searchMealsByName(
        @Query("s") name : String
    ) : MealResponseDto

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ) : MealResponseDto

    @GET("random.php")
    suspend fun getRandomMeal() : MealResponseDto
}