package org.mohsen.reviewtask.presentation.screens.dynamicui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mohsen.reviewtask.domain.model.UiComponent
import org.mohsen.reviewtask.domain.usecase.GetUiComponentUseCase

sealed class UiState {
    data class Loading(val isRefresh: Boolean = false) : UiState()
    data class Success(val component: UiComponent) : UiState()
    data class Error(val message: String? = null) : UiState()
}

class DynamicUiViewModel(
    private val getUiComponentUseCase: GetUiComponentUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { UiState.Loading(isRefresh) }

            try {
                val component = getUiComponentUseCase()
                _uiState.update { UiState.Success(component) }
            } catch (e: Exception) {
                _uiState.update { UiState.Error(e.message) }
            }
        }
    }

    fun updateUiComponent(update: UiComponent) {
        _uiState.update { UiState.Success(update) }
    }
}
