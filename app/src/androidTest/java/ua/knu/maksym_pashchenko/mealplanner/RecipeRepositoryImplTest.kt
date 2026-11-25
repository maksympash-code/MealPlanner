
package ua.knu.maksym_pashchenko.mealplanner

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ua.knu.maksym_pashchenko.mealplanner.data.local.AppDatabase
import ua.knu.maksym_pashchenko.mealplanner.data.remote.dto.MealDto
import ua.knu.maksym_pashchenko.mealplanner.data.remote.dto.MealResponseDto
import ua.knu.maksym_pashchenko.mealplanner.data.repository.FavoritesRepositoryImpl
import ua.knu.maksym_pashchenko.mealplanner.data.repository.RecipeRepositoryImpl
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Ingredient
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.FavoritesRepository
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.RecipeRepository
import ua.knu.maksym_pashchenko.mealplanner.fake.FakeMealApiService
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RecipeRepositoryImplTest {

    private lateinit var database: AppDatabase

    private lateinit var fakeApi: FakeMealApiService

    private lateinit var recipeRepository: RecipeRepository

    private lateinit var favoritesRepository: FavoritesRepository

    @Before
    fun setUp() {
        val context =
            ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder<AppDatabase>(
            context
        ).build()

        fakeApi = FakeMealApiService()

        recipeRepository = RecipeRepositoryImpl(
            api = fakeApi,
            recipeDao = database.recipeDao()
        )

        favoritesRepository = FavoritesRepositoryImpl(
            database = database,
            favoriteDao = database.favoriteDao(),
            recipeDao = database.recipeDao(),
            ingredientDao = database.ingredientDao()
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    // TEST 1: Load local recipe with ingredients

    @Test
    fun getRecipeById_localRecipeExists_returnsRecipeWithIngredients() =
        runBlocking {
            // Arrange
            val expectedRecipe = localRecipe()

            favoritesRepository.addFavorite(expectedRecipe)

            // Act
            val actualRecipe =
                recipeRepository.getRecipeById(expectedRecipe.id)

            // Assert
            assertRecipeEquals(expectedRecipe, actualRecipe)

            assertEquals(0, fakeApi.lookupCallCount)
            assertNull(fakeApi.lastLookupId)
        }

    // TEST 2: Local recipe is available when API fails

    @Test
    fun getRecipeById_localRecipeExists_apiUnavailable_returnsLocalRecipe() =
        runBlocking {
            // Arrange
            val expectedRecipe = localRecipe()

            favoritesRepository.addFavorite(expectedRecipe)

            fakeApi.lookupException =
                IOException("No internet connection")

            // Act
            val actualRecipe =
                recipeRepository.getRecipeById(expectedRecipe.id)

            // Assert
            assertRecipeEquals(expectedRecipe, actualRecipe)

            assertEquals(0, fakeApi.lookupCallCount)
        }

    // TEST 3: Fallback to API when recipe is not cached

    @Test
    fun getRecipeById_localRecipeMissing_fetchesRecipeFromApi() =
        runBlocking {
            // Arrange
            val recipeId = "remote-001"

            fakeApi.lookupResponse = MealResponseDto(
                meals = listOf(remoteMealDto(recipeId))
            )

            val expectedRecipe = Recipe(
                id = recipeId,
                name = "Remote Chicken",
                category = "Chicken",
                area = "American",
                instructions = "Cook the remote chicken",
                imageUrl = "https://example.com/remote-chicken.jpg",
                ingredients = listOf(
                    Ingredient(
                        name = "Chicken",
                        measure = "500 g"
                    ),
                    Ingredient(
                        name = "Pepper",
                        measure = "2 tsp"
                    )
                )
            )

            assertNull(
                database.recipeDao().getRecipeById(recipeId)
            )

            // Act
            val actualRecipe =
                recipeRepository.getRecipeById(recipeId)

            // Assert
            assertRecipeEquals(expectedRecipe, actualRecipe)

            assertEquals(1, fakeApi.lookupCallCount)
            assertEquals(recipeId, fakeApi.lastLookupId)

            // API responses are not automatically cached
            assertNull(
                database.recipeDao().getRecipeById(recipeId)
            )
        }

    // TEST 4: Local version has priority over remote version

    @Test
    fun getRecipeById_localAndRemoteVersionsExist_prefersLocalVersion() =
        runBlocking {
            // Arrange
            val expectedRecipe = localRecipe()

            favoritesRepository.addFavorite(expectedRecipe)

            fakeApi.lookupResponse = MealResponseDto(
                meals = listOf(
                    remoteMealDto(expectedRecipe.id)
                )
            )

            // Act
            val actualRecipe =
                recipeRepository.getRecipeById(expectedRecipe.id)

            // Assert
            assertRecipeEquals(expectedRecipe, actualRecipe)

            assertEquals("Local Chicken", actualRecipe?.name)

            assertEquals(
                "Cook the local chicken",
                actualRecipe?.instructions
            )

            assertEquals(0, fakeApi.lookupCallCount)
        }

    // TEST 5: Recipe does not exist locally or remotely

    @Test
    fun getRecipeById_recipeMissingEverywhere_returnsNull() =
        runBlocking {
            // Arrange
            val recipeId = "missing-001"

            fakeApi.lookupResponse =
                MealResponseDto(meals = null)

            // Act
            val actualRecipe =
                recipeRepository.getRecipeById(recipeId)

            // Assert
            assertNull(actualRecipe)

            assertEquals(1, fakeApi.lookupCallCount)
            assertEquals(recipeId, fakeApi.lastLookupId)
        }

    // TEST 6: Removing favorite keeps local recipe available

    @Test
    fun getRecipeById_favoriteRemoved_returnsLocalRecipe() =
        runBlocking {
            // Arrange
            val expectedRecipe = localRecipe()

            favoritesRepository.addFavorite(expectedRecipe)

            favoritesRepository.removeFavorite(expectedRecipe.id)

            fakeApi.lookupException =
                IOException("No internet connection")

            assertFalse(
                favoritesRepository
                    .isFavorite(expectedRecipe.id)
                    .first()
            )

            // Act
            val actualRecipe =
                recipeRepository.getRecipeById(expectedRecipe.id)

            // Assert
            assertRecipeEquals(expectedRecipe, actualRecipe)

            assertEquals(0, fakeApi.lookupCallCount)

            assertTrue(
                favoritesRepository.observeFavorites().first().isEmpty()
            )
        }

    // TEST 7: Network exception is propagated on cache miss

    @Test
    fun getRecipeById_localRecipeMissing_apiFails_propagatesException() =
        runBlocking {
            // Arrange
            val recipeId = "offline-001"

            val expectedException =
                IOException("No internet connection")

            fakeApi.lookupException = expectedException

            // Act & Assert
            try {
                recipeRepository.getRecipeById(recipeId)

                fail("Expected IOException to be thrown")
            } catch (exception: IOException) {
                assertSame(expectedException, exception)

                assertEquals(
                    "No internet connection",
                    exception.message
                )
            }

            assertEquals(1, fakeApi.lookupCallCount)
            assertEquals(recipeId, fakeApi.lastLookupId)
        }

    // Shared assertion helper

    private fun assertRecipeEquals(
        expected: Recipe,
        actual: Recipe?
    ) {
        assertNotNull(actual)

        val actualRecipe = requireNotNull(actual)

        assertEquals(expected.id, actualRecipe.id)
        assertEquals(expected.name, actualRecipe.name)
        assertEquals(expected.category, actualRecipe.category)
        assertEquals(expected.area, actualRecipe.area)
        assertEquals(expected.instructions, actualRecipe.instructions)
        assertEquals(expected.imageUrl, actualRecipe.imageUrl)

        assertEquals(
            expected.ingredients.size,
            actualRecipe.ingredients.size
        )

        assertEquals(
            expected.ingredients.groupingBy { it }.eachCount(),
            actualRecipe.ingredients.groupingBy { it }.eachCount()
        )
    }

    // Shared local test data

    private fun localRecipe(): Recipe = Recipe(
        id = "local-001",
        name = "Local Chicken",
        category = "Chicken",
        area = "Ukraine",
        instructions = "Cook the local chicken",
        imageUrl = "https://example.com/local-chicken.jpg",
        ingredients = listOf(
            Ingredient(
                name = "Chicken",
                measure = "1 kg"
            ),
            Ingredient(
                name = "Salt",
                measure = "1 tsp"
            )
        )
    )

    // Shared remote test data

    private fun remoteMealDto(recipeId: String): MealDto =
        MealDto(
            id = recipeId,
            name = "Remote Chicken",
            category = "Chicken",
            area = "American",
            instructions = "Cook the remote chicken",
            image = "https://example.com/remote-chicken.jpg",
            firstIngredient = "Chicken",
            firstMeasure = "500 g",
            secondIngredient = "Pepper",
            secondMeasure = "2 tsp"
        )
}
