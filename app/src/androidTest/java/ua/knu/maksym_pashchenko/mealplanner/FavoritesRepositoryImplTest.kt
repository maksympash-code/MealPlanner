
package ua.knu.maksym_pashchenko.mealplanner

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ua.knu.maksym_pashchenko.mealplanner.data.local.AppDatabase
import ua.knu.maksym_pashchenko.mealplanner.data.local.entity.RecipeEntity
import ua.knu.maksym_pashchenko.mealplanner.data.repository.FavoritesRepositoryImpl
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Ingredient
import ua.knu.maksym_pashchenko.mealplanner.domain.model.Recipe
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.FavoritesRepository
import kotlin.time.Duration.Companion.milliseconds

@RunWith(AndroidJUnit4::class)
class FavoritesRepositoryImplTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: FavoritesRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder<AppDatabase>(context)
            .build()

        repository = FavoritesRepositoryImpl(
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

    // TEST 1: Saving recipe and ingredients

    @Test
    fun addFavorite_savesRecipeAndIngredients() = runBlocking {
        val recipe = testRecipe()

        repository.addFavorite(recipe)

        val savedRecipe =
            database.recipeDao().getRecipeById(recipe.id)

        val savedIngredients =
            database.ingredientDao()
                .getIngredientsByRecipeId(recipe.id)

        assertNotNull(savedRecipe)
        assertEquals(recipe.id, savedRecipe?.recipeId)
        assertEquals(recipe.name, savedRecipe?.recipeName)

        assertEquals(2, savedIngredients.size)

        val actualIngredients = savedIngredients
            .map { it.ingredientName to it.ingredientMeasure }
            .toSet()

        val expectedIngredients = setOf(
            "Chicken" to "1",
            "Salt" to "1/4"
        )

        assertEquals(expectedIngredients, actualIngredients)
    }

    // TEST 2: No duplicates after adding twice

    @Test
    fun addFavorite_calledTwice_doesNotDuplicateData() = runBlocking {
        val recipe = testRecipe()

        repository.addFavorite(recipe)
        repository.addFavorite(recipe)

        val savedIngredients =
            database.ingredientDao()
                .getIngredientsByRecipeId(recipe.id)

        val favorites =
            database.favoriteDao().getAllFavorites().first()

        assertEquals(2, savedIngredients.size)
        assertEquals(1, favorites.size)
        assertEquals(recipe.id, favorites.single().recipeId)

        assertEquals(
            setOf("Chicken", "Salt"),
            savedIngredients.map { it.ingredientName }.toSet()
        )
    }

    // TEST 3: Updating an existing favorite

    @Test
    fun addFavorite_updatesRecipeAndReplacesIngredients() = runBlocking {
        val original = testRecipe()

        val updated = Recipe(
            id = original.id,
            name = "Updated Chicken",
            category = original.category,
            area = original.area,
            imageUrl = original.imageUrl,
            instructions = original.instructions,
            ingredients = listOf(
                Ingredient(
                    name = "Pepper",
                    measure = "2 tsp"
                )
            )
        )

        repository.addFavorite(original)
        repository.addFavorite(updated)

        val savedRecipe =
            database.recipeDao().getRecipeById(original.id)

        val savedIngredients =
            database.ingredientDao()
                .getIngredientsByRecipeId(original.id)

        assertEquals("Updated Chicken", savedRecipe?.recipeName)

        assertEquals(1, savedIngredients.size)
        assertEquals("Pepper", savedIngredients.single().ingredientName)
        assertEquals("2 tsp", savedIngredients.single().ingredientMeasure)

        assertEquals(
            1,
            database.favoriteDao().getAllFavorites().first().size
        )
    }

    // TEST 4: Removing favorite keeps recipe data

    @Test
    fun removeFavorite_keepsRecipeAndIngredients() = runBlocking {
        val recipe = testRecipe()

        repository.addFavorite(recipe)
        repository.removeFavorite(recipe.id)

        val favorites =
            database.favoriteDao().getAllFavorites().first()

        val savedRecipe =
            database.recipeDao().getRecipeById(recipe.id)

        val savedIngredients =
            database.ingredientDao()
                .getIngredientsByRecipeId(recipe.id)

        assertTrue(favorites.isEmpty())
        assertFalse(repository.isFavorite(recipe.id).first())

        assertNotNull(savedRecipe)
        assertEquals(recipe.id, savedRecipe?.recipeId)
        assertEquals(2, savedIngredients.size)
    }

    // TEST 5: Reactive favorite status

    @Test
    fun isFavorite_emitsFalseTrueFalse() = runBlocking {
        val recipe = testRecipe()
        val states = Channel<Boolean>(Channel.UNLIMITED)

        val collector = launch {
            repository.isFavorite(recipe.id).collect {
                states.send(it)
            }
        }

        try {
            // Initial state
            assertFalse(
                withTimeout(5_000.milliseconds) { states.receive() }
            )

            // Add favorite
            repository.addFavorite(recipe)

            val afterAdd = withTimeout(5_000.milliseconds) {
                states.receiveAsFlow().first { it }
            }

            assertTrue(afterAdd)

            // Remove favorite
            repository.removeFavorite(recipe.id)

            val afterRemove = withTimeout(5_000.milliseconds) {
                states.receiveAsFlow().first { !it }
            }

            assertFalse(afterRemove)
        } finally {
            collector.cancelAndJoin()
            states.close()
        }
    }

    // TEST 6: Reactive favorites list

    @Test
    fun observeFavorites_emitsOnAddAndRemove() = runBlocking {
        val recipe = testRecipe()
        val emissions = Channel<List<Recipe>>(Channel.UNLIMITED)

        val collector = launch {
            repository.observeFavorites().collect {
                emissions.send(it)
            }
        }

        try {
            // Initially empty
            val initial = withTimeout(5_000.milliseconds) {
                emissions.receive()
            }

            assertTrue(initial.isEmpty())

            // Add recipe
            repository.addFavorite(recipe)

            val afterAdd = withTimeout(5_000.milliseconds) {
                emissions.receiveAsFlow().first { list ->
                    list.any { it.id == recipe.id }
                }
            }

            assertEquals(1, afterAdd.size)

            val savedRecipe = afterAdd.single()

            assertEquals(recipe.id, savedRecipe.id)
            assertEquals(recipe.name, savedRecipe.name)
            assertEquals(recipe.category, savedRecipe.category)
            assertEquals(2, savedRecipe.ingredients.size)

            val actualIngredients = savedRecipe.ingredients
                .map { it.name to it.measure }
                .toSet()

            assertEquals(
                setOf("Chicken" to "1", "Salt" to "1/4"),
                actualIngredients
            )

            // Remove recipe from favorites
            repository.removeFavorite(recipe.id)

            val afterRemove = withTimeout(5_000.milliseconds) {
                emissions.receiveAsFlow().first { it.isEmpty() }
            }

            assertTrue(afterRemove.isEmpty())
        } finally {
            collector.cancelAndJoin()
            emissions.close()
        }
    }

    // TEST 7: JOIN returns only favorite recipes

    @Test
    fun observeFavorites_returnsOnlyFavoriteRecipes() = runBlocking {
        val favoriteRecipe = testRecipe()

        // Store a recipe without marking it as favorite
        database.recipeDao().insertRecipe(
            RecipeEntity(
                recipeId = "not-favorite",
                recipeName = "Other Recipe",
                category = null,
                area = null,
                imageUrl = null,
                instructions = null
            )
        )

        // Store a favorite recipe
        repository.addFavorite(favoriteRecipe)

        val favorites = repository.observeFavorites().first()

        assertEquals(1, favorites.size)
        assertEquals(favoriteRecipe.id, favorites.single().id)
        assertEquals(favoriteRecipe.name, favorites.single().name)
    }


    // TEST 8: Cascade deletion removes related records

    @Test
    fun deleteRecipe_cascadesToFavoritesAndIngredients() = runBlocking {
        // Arrange
        val recipe = testRecipe()

        val anotherRecipe = recipe.copy(
            id = "test-002",
            name = "Another Chicken"
        )

        repository.addFavorite(recipe)
        repository.addFavorite(anotherRecipe)

        assertNotNull(
            database.recipeDao().getRecipeById(recipe.id)
        )

        assertTrue(
            repository.isFavorite(recipe.id).first()
        )

        assertEquals(
            recipe.ingredients.size,
            database.ingredientDao()
                .getIngredientsByRecipeId(recipe.id)
                .size
        )

        // Act
        database.withTransaction {
            database.openHelper.writableDatabase.execSQL(
                "DELETE FROM recipes WHERE recipeId = ?",
                arrayOf(recipe.id)
            )
        }

        // Assert: Parent recipe was deleted
        assertNull(
            database.recipeDao().getRecipeById(recipe.id)
        )

        // Assert: Favorite was deleted automatically
        assertFalse(
            repository.isFavorite(recipe.id).first()
        )

        val favoriteEntities =
            database.favoriteDao().getAllFavorites().first()

        assertFalse(
            favoriteEntities.any { it.recipeId == recipe.id }
        )

        // Assert: Ingredients were deleted automatically
        assertTrue(
            database.ingredientDao()
                .getIngredientsByRecipeId(recipe.id)
                .isEmpty()
        )

        // Assert: Unrelated recipe remains untouched
        assertNotNull(
            database.recipeDao().getRecipeById(anotherRecipe.id)
        )

        assertTrue(
            repository.isFavorite(anotherRecipe.id).first()
        )

        assertEquals(
            anotherRecipe.ingredients.size,
            database.ingredientDao()
                .getIngredientsByRecipeId(anotherRecipe.id)
                .size
        )

        assertEquals(
            1,
            favoriteEntities.size
        )

        assertEquals(
            anotherRecipe.id,
            favoriteEntities.single().recipeId
        )
    }


    // TEST 9: Adding recipe through toggleFavorite

    @Test
    fun toggleFavorite_notFavorite_addsRecipeAndIngredients() = runBlocking {
            // Arrange
            val recipe = testRecipe()

            assertFalse(
                repository.isFavorite(recipe.id).first()
            )

            assertNull(
                database.recipeDao().getRecipeById(recipe.id)
            )

            // Act
            repository.toggleFavorite(recipe)

            // Assert
            assertToggleState(
                recipe = recipe,
                expectedFavorite = true
            )
        }

    // TEST 10: Removing recipe through toggleFavorite

    @Test
    fun toggleFavorite_alreadyFavorite_removesOnlyFavorite() =
        runBlocking {
            // Arrange
            val recipe = testRecipe()

            repository.addFavorite(recipe)

            assertTrue(
                repository.isFavorite(recipe.id).first()
            )

            // Act
            repository.toggleFavorite(recipe)

            // Assert
            assertToggleState(
                recipe = recipe,
                expectedFavorite = false
            )
        }


    // TEST 11: Two sequential toggles

    @Test
    fun toggleFavorite_calledTwice_restoresInitialState() =
        runBlocking {
            // Arrange
            val recipe = testRecipe()

            assertFalse(
                repository.isFavorite(recipe.id).first()
            )

            // Act: First toggle
            repository.toggleFavorite(recipe)

            // Assert: Added
            assertToggleState(
                recipe = recipe,
                expectedFavorite = true
            )

            // Act: Second toggle
            repository.toggleFavorite(recipe)

            // Assert: Removed
            assertToggleState(
                recipe = recipe,
                expectedFavorite = false
            )
        }

    // TEST 12: Even number of concurrent toggles

    @Test
    fun toggleFavorite_concurrentEvenToggles_restoresInitialState() =
        runBlocking {
            // Arrange
            val recipe = testRecipe()

            assertFalse(
                repository.isFavorite(recipe.id).first()
            )

            // Act
            withTimeout(15_000.milliseconds) {
                performConcurrentToggles(
                    recipe = recipe,
                    count = 20
                )
            }

            // Assert
            assertToggleState(
                recipe = recipe,
                expectedFavorite = false
            )
        }

    // TEST 13: Odd number of concurrent toggles

    @Test
    fun toggleFavorite_concurrentOddToggles_invertsInitialState() =
        runBlocking {
            // Arrange
            val recipe = testRecipe()

            assertFalse(
                repository.isFavorite(recipe.id).first()
            )

            // Act
            withTimeout(15_000.milliseconds) {
                performConcurrentToggles(
                    recipe = recipe,
                    count = 21
                )
            }

            // Assert
            assertToggleState(
                recipe = recipe,
                expectedFavorite = true
            )
        }

    // Shared assertion helper

    private suspend fun assertToggleState(
        recipe: Recipe,
        expectedFavorite: Boolean
    ) {
        // Check reactive favorite status
        assertEquals(
            expectedFavorite,
            repository.isFavorite(recipe.id).first()
        )

        // Check favorites table
        val favoriteEntities =
            database.favoriteDao().getAllFavorites().first()

        assertEquals(
            if (expectedFavorite) 1 else 0,
            favoriteEntities.size
        )

        if (expectedFavorite) {
            assertEquals(
                recipe.id,
                favoriteEntities.single().recipeId
            )
        }

        // Check observable favorites list
        val favorites =
            repository.observeFavorites().first()

        assertEquals(
            if (expectedFavorite) 1 else 0,
            favorites.size
        )

        if (expectedFavorite) {
            assertEquals(
                recipe.id,
                favorites.single().id
            )
        }

        // Check stored recipe
        val savedRecipe =
            database.recipeDao().getRecipeById(recipe.id)

        assertNotNull(savedRecipe)

        val actualRecipe = requireNotNull(savedRecipe)

        assertEquals(recipe.id, actualRecipe.recipeId)
        assertEquals(recipe.name, actualRecipe.recipeName)
        assertEquals(recipe.category, actualRecipe.category)
        assertEquals(recipe.area, actualRecipe.area)
        assertEquals(recipe.imageUrl, actualRecipe.imageUrl)
        assertEquals(recipe.instructions, actualRecipe.instructions)

        // Check stored ingredients
        val savedIngredients =
            database.ingredientDao()
                .getIngredientsByRecipeId(recipe.id)

        assertEquals(
            recipe.ingredients.size,
            savedIngredients.size
        )

        val expectedIngredients = recipe.ingredients
            .map { it.name to it.measure }
            .groupingBy { it }
            .eachCount()

        val actualIngredients = savedIngredients
            .map { it.ingredientName to it.ingredientMeasure }
            .groupingBy { it }
            .eachCount()

        assertEquals(
            expectedIngredients,
            actualIngredients
        )
    }

    // Shared concurrency helper

    private suspend fun performConcurrentToggles(
        recipe: Recipe,
        count: Int
    ) = coroutineScope {

        val startSignal = CompletableDeferred<Unit>()

        val readySignals = List(count) {
            CompletableDeferred<Unit>()
        }

        repeat(count) { index ->

            launch(Dispatchers.Default) {

                // Signal that this coroutine is ready
                readySignals[index].complete(Unit)

                // Wait for the common start signal
                startSignal.await()

                // Execute atomic toggle
                repository.toggleFavorite(recipe)
            }
        }

        // Wait until all coroutines are ready
        readySignals.forEach { signal ->
            signal.await()
        }

        // Allow all coroutines to proceed
        startSignal.complete(Unit)
    }


    private fun testRecipe(): Recipe = Recipe(
        id = "test-001",
        name = "Test Chicken",
        category = "Chicken",
        area = "Ukraine",
        imageUrl = null,
        instructions = null,
        ingredients = listOf(
            Ingredient(
                name = "Chicken",
                measure = "1"
            ),
            Ingredient(
                name = "Salt",
                measure = "1/4"
            )
        )
    )
}
