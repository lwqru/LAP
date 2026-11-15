package com.example.studyplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun TodayRoute(viewModel: TodayViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFocusMode = rememberAirplaneModeOn()                    // new
    AskNotificationPermission()                                   // new
    TodayScreen(
        uiState = uiState,
        isFocusMode = isFocusMode,                                // new
        onAddTask = { title -> viewModel.onAddTask(title) },
        onTaskCheckedChange = { task, done ->
            viewModel.onTaskCheckedChange(task, done)
        },
        onDeleteDone = { viewModel.onDeleteDone() },
        onMessageShown = { viewModel.onMessageShown() },
        onRemindMe = { viewModel.onRemindMe() }                   // new
    )
}

@Composable
fun TaskRow(task: StudyTask, onCheckedChange: (StudyTask, Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { done -> onCheckedChange(task, done) }
        )
        Column {
            Text(task.title)
            Text(task.course, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun TodayScreen(
    uiState: TodayUiState,
    isFocusMode: Boolean,                                         // new
    onAddTask: (String) -> Unit,
    onTaskCheckedChange: (StudyTask, Boolean) -> Unit,
    onDeleteDone: () -> Unit,
    onMessageShown: () -> Unit,
    onRemindMe: () -> Unit                                        // new
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.message) {
        val message = uiState.message
        if (message != null) {
            snackbarHostState.showSnackbar(message)
            onMessageShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Today", Modifier.weight(1f),
                    style = MaterialTheme.typography.headlineMedium)
                TextButton(onClick = onRemindMe) { Text("Remind me") }   // new
                TextButton(onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Tick a task when you finish it")
                    }
                }) { Text("Help") }
            }
            TextButton(onClick = onDeleteDone) { Text("Clear done") }
            if (isFocusMode) {                                           // new
                Card(Modifier.fillMaxWidth()) {
                    Text(
                        "Focus mode: airplane mode is on. No distractions!",
                        Modifier.padding(12.dp)
                    )
                }
            }
            AddTaskRow(onAddTask)
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.tasks.isEmpty() ->
                    Text("No tasks yet. Add your first one above.")
                else -> uiState.tasks.forEach { task ->
                    TaskRow(task, onTaskCheckedChange)
                }
            }
        }
    }
}

@Composable
fun AddTaskRow(onAddTask: (String) -> Unit) {
    var newTitle by rememberSaveable { mutableStateOf("") }

    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = newTitle,
            onValueChange = { newTitle = it },
            label = { Text("New task") },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        Button(onClick = {
            onAddTask(newTitle)
            newTitle = ""
        }) { Text("Add") }
    }
}
