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
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes.state.RecipesResultState
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.recipes.state.RecipesUiState
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
                    resultState = RecipesResultState.Initial
                )
            }
        }
    }

    fun retrySearch() {
        _uiState.update { state ->
            if (
                state.resultState is RecipesResultState.Error &&
                state.query.isNotBlank()
            ) {
                state.copy(retryCount = state.retryCount + 1)
            } else {
                state
            }
        }
    }

    private fun automaticRecipeSearch() {
        viewModelScope.launch {
            _uiState.map { state ->
                state.query to state.retryCount
            }
                .distinctUntilChanged()
                .collectLatest { pairOfState ->
                    val trimmedQuery = pairOfState.first.trim()

                    if (trimmedQuery.isBlank()) {
                        return@collectLatest
                    }

                    delay(350.milliseconds)

                    _uiState.update { state ->
                        if (isCurrentRequest(state, pairOfState)) {
                            state.copy(resultState = RecipesResultState.Loading)
                        } else
                            state
                    }

                    try {
                        val recipes = repository.searchRecipes(trimmedQuery)

                        _uiState.update { state ->
                            if (isCurrentRequest(state, pairOfState)) {
                                state.copy(resultState =
                                    if (recipes.isEmpty()) {
                                        RecipesResultState.Empty
                                    } else {
                                        RecipesResultState.Success(recipes)
                                    }
                                )
                            } else
                                state
                        }

                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        _uiState.update { state ->
                            if (isCurrentRequest(state, pairOfState)) {
                                state.copy(resultState = RecipesResultState.Error(e.message ?: "Не вдалося завантажити рецепти"))
                            }
                            else
                                state
                        }
                    }
                }
        }
    }


    private fun isCurrentRequest(
        state: RecipesUiState,
        request: Pair<String, Int>
    ) : Boolean {
        return state.query == request.first
                && state.retryCount == request.second
    }

    init {
        automaticRecipeSearch()
    }
}