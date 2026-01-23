package com.example.mealplanner.presentation.screen.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.domain.repositories.ShoppingRepository
import com.example.mealplanner.presentation.screen.shoppinglist.ShoppingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val shoppingRepo: ShoppingRepository
) : ViewModel() {

    private val itemsFlow = shoppingRepo.getShoppingList()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState>
        get() = _uiState

    init {
        viewModelScope.launch {
            itemsFlow.collect { items ->
                _uiState.update { it.copy(items = items) }
            }
        }
    }

    fun onClearCheckedClick() {
        viewModelScope.launch {
            runCatching { shoppingRepo.clearChecked() }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "Failed to clear checked") }
                }
        }
    }

    fun onToggleCheckedClick(itemId: Int) {
        viewModelScope.launch {
            runCatching { shoppingRepo.toggleChecked(itemId) }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "Failed to toggle") }
                }
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            runCatching { shoppingRepo.deleteItem(itemId) }
                .onFailure { e ->
                    _uiState.update { it.copy(error = e.message ?: "Failed to delete") }
                }
        }
    }

    fun consumeError() {
        _uiState.update { it.copy(error = null) }
    }
}