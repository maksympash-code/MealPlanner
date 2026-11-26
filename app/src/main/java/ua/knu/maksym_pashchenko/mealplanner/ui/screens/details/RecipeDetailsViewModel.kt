package ua.knu.maksym_pashchenko.mealplanner.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.FavoritesRepository
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.RecipeRepository
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.details.state.RecipeDetailsUiState
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.details.state.RecipeDetailsUiState.Success
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    private val recipeId: String? = savedStateHandle.get<String>(key = "recipeId")

    private val _favoriteReadError = MutableStateFlow<String?>(null)
    val favoriteReadError: StateFlow<String?> = _favoriteReadError.asStateFlow()

    private val _favoriteWriteError = MutableStateFlow<String?>(null)

    val favoriteError: StateFlow<String?> =
        combine(_favoriteReadError, _favoriteWriteError) { read, write ->
            read ?: write
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000.milliseconds),
        initialValue = null
    )

    private val _favoriteRetryTrigger = MutableStateFlow(0)

    val isFavorite: StateFlow<Boolean?> =
        _favoriteRetryTrigger.flatMapLatest {
            val source: Flow<Boolean?> = if (recipeId != null) {
                favoritesRepository.isFavorite(recipeId)
            } else {
                flowOf(null)
            }

            source
                .onEach { status ->
                if (status != null) {
                    _favoriteReadError.value = null
                }
            }.catch {throwable ->
                _favoriteReadError.value = throwable.message ?: "Unknown error"
                emit(null)
            }

        }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000.milliseconds),
        initialValue = null
    )

    private val _isUpdatingFavorite = MutableStateFlow(false)
    val isUpdatingFavorite: StateFlow<Boolean> = _isUpdatingFavorite.asStateFlow()

    private fun loadRecipeDetails(recipeId: String) {
        viewModelScope.launch {
            try {
                val recipe = recipeRepository.getRecipeById(recipeId)

                if (recipe == null) {
                    _uiState.value = RecipeDetailsUiState.Error(message = "Recipe not found")
                    return@launch
                }

                _uiState.value = Success(recipe)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = RecipeDetailsUiState.Error(message = e.toString())
            }
        }
    }

    fun toggleFavorite() {
        val currentState = uiState.value

        if (currentState !is Success)
            return

        if (isFavorite.value == null)
            return

        if (isUpdatingFavorite.value)
            return

        _isUpdatingFavorite.value = true
        _favoriteWriteError.value = null
        val recipe = currentState.recipe

        viewModelScope.launch {

            try {
                favoritesRepository.toggleFavorite(recipe)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _favoriteWriteError.value = e.message ?: "Unknown error"
            } finally {
                _isUpdatingFavorite.value = false
            }
        }

    }


    fun retryFavoriteStatus() {
        _favoriteRetryTrigger.update { current ->
            current + 1
        }
    }

    init {
        if (recipeId != null) {
            loadRecipeDetails(recipeId)
        }
        else {
            _uiState.value = RecipeDetailsUiState.Error("Recipe ID is missing")
        }
    }
}