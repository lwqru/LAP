package com.example.studyplanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodayViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = TaskRepository(
        StudyDatabase.getDatabase(application).studyTaskDao()
    )

    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allTasks.collect { tasks ->
                _uiState.update {
                    it.copy(isLoading = false, tasks = tasks)
                }
            }
        }
    }

    fun onAddTask(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.add(title.trim())
            _uiState.update { it.copy(message = "Task added") }
        }
    }

    fun onTaskCheckedChange(task: StudyTask, done: Boolean) {
        viewModelScope.launch {
            repository.setDone(task, done)
            if (done) _uiState.update { it.copy(message = "Marked as done") }
        }
    }

    // Lecture 10 challenge
    fun onDeleteDone() {
        viewModelScope.launch {
            repository.deleteDone()
            _uiState.update { it.copy(message = "Done tasks cleared") }
        }
    }

    fun onRemindMe() {                                              // new
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(getApplication()).enqueue(request)
        _uiState.update {
            it.copy(message = "Reminder in 10 seconds. Close the app!")
        }
    }

    fun onMessageShown() {
        _uiState.update { it.copy(message = null) }
    }
}
