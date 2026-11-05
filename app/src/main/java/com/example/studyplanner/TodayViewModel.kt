package com.example.studyplanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodayViewModel : ViewModel() {

    private val repository = FakeTaskRepository()

    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    // Loading starts here
    init {
        viewModelScope.launch {
            val tasks = repository.getTodayTasks()
            _uiState.update { it.copy(isLoading = false, tasks = tasks) }
        }
    }

    fun onTaskChecked(id: Int, checked: Boolean) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    tasks = state.tasks.map { if (it.id == id) it.copy(isDone = checked) else it },
                    message = if (checked) "Marked as done" else null
                )
            }
        }
    }

    fun onMessageShown() {
        _uiState.update { it.copy(message = null) }
    }
}
