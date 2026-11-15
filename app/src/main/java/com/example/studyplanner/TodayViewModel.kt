package com.example.studyplanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodayViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TaskRepository(
        StudyDatabase.getDatabase(application).studyTaskDao()
    )

    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    // Collect the Flow once: every time the table changes, Room sends a new list
    init {
        viewModelScope.launch {
            repository.allTasks.collect { tasks ->
                _uiState.update { it.copy(isLoading = false, tasks = tasks) }
            }
        }
    }

    // Events only WRITE to the database; they never change the list themselves
    fun onAddTask(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.add(title.trim())
            _uiState.update { it.copy(message = "Task added") }
        }
    }

    fun onTaskCheckedChange(task: StudyTask, checked: Boolean) {
        viewModelScope.launch {
            repository.setDone(task, checked)
            if (checked) _uiState.update { it.copy(message = "Marked as done") }
        }
    }

    // Challenge
    fun onDeleteDone() {
        viewModelScope.launch {
            repository.deleteDone()
            _uiState.update { it.copy(message = "Done tasks cleared") }
        }
    }

    fun onMessageShown() {
        _uiState.update { it.copy(message = null) }
    }
}
