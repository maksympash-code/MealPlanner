package ua.knu.maksym_pashchenko.mealplanner.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.knu.maksym_pashchenko.mealplanner.domain.repository.FavoritesRepository
import ua.knu.maksym_pashchenko.mealplanner.ui.screens.favorites.state.FavoritesUiState
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = repository.observeFavorites()
        .map { listRecipes ->
            if (listRecipes.isEmpty()) {
                FavoritesUiState.Empty
            } else {
                FavoritesUiState.Success(listRecipes)
            }
    }
        .catch {exception ->
            emit(FavoritesUiState.Error(message = exception.message ?: "Unknown error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000.milliseconds),
            initialValue = FavoritesUiState.Loading
        )
}