package ua.knu.maksym_pashchenko.mealplanner.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.RecipeRepository
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.details.state.RecipeDetailsUiState
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    private val recipeId: String? = savedStateHandle.get<String>(key = "recipeId")

    private fun loadRecipeDetails(recipeId: String) {
        viewModelScope.launch {
            try {
                val recipe = repository.getRecipeById(recipeId)

                if (recipe == null) {
                    _uiState.value = RecipeDetailsUiState.Error(message = "Recipe ID is missing")
                    return@launch
                }

                _uiState.value = RecipeDetailsUiState.Success(recipe)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = RecipeDetailsUiState.Error(message = e.toString())
            }
        }
    }

    init {
        if (recipeId != null)
            loadRecipeDetails(recipeId)
        else {
            _uiState.value = RecipeDetailsUiState.Error("Recipe not found")
        }
    }
}