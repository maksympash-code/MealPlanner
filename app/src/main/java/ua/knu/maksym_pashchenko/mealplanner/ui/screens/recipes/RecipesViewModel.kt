package ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.RecipeRepository
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState : StateFlow<RecipesUiState> = _uiState.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _uiState.update { state ->
            if (state.query == newQuery){
                state
            } else {
                state.copy(
                    query = newQuery,
                    recipes = emptyList(),
                    isLoading = false,
                    error = null,
                    hasSearched = false
                )
            }


        }
    }

    private fun automaticRecipeSearch() {
        viewModelScope.launch {
            _uiState.map { state ->
                state.query
            }
                .distinctUntilChanged()
                .collectLatest { query ->
                    val trimmedQuery = query.trim()

                    if (trimmedQuery.isBlank()) {
                        return@collectLatest
                    }

                    delay(350.milliseconds)

                    _uiState.update { state ->
                        state.copy(isLoading = true, error = null)
                    }

                    try {
                        val recipes = repository.searchRecipes(trimmedQuery)

                        _uiState.update { state ->
                            if (state.query == query)
                                state.copy(recipes = recipes, hasSearched = true)
                            else
                                state
                        }

                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        _uiState.update { state ->
                            if (state.query == query)
                                state.copy(error = e.message ?: "Не вдалося завантажити рецепти")
                            else
                                state
                        }
                    } finally {
                        _uiState.update { state ->
                            if (state.query == query)
                                state.copy(isLoading = false)
                            else
                                state
                        }
                    }
                }
        }
    }

    init {
        automaticRecipeSearch()
    }
}