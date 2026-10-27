package com.example.studyplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StudyPlannerRoute(viewModel: StudyPlannerViewModel = viewModel()) {

    // TODO 2 — draft text state that survives rotation
    var draft by rememberSaveable { mutableStateOf("") }

    StudyPlannerScreen(
        uiState = viewModel.uiState,
        draft = draft,
        onDraftChange = {
            // TODO 3 — store the new text in draft
            draft = it
        },
        onAddTask = {
            // TODO 4 — ask the ViewModel to add the draft, then clear draft
            viewModel.addTask(draft)
            draft = ""
        }
    )
}

@Composable
fun StudyPlannerScreen(
    uiState: StudyPlannerUiState,
    draft: String,
    onDraftChange: (String) -> Unit,
    onAddTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        TaskCount(count = uiState.tasks.size)

        // TODO 5 — Show AddTaskRow here
        AddTaskRow(text = draft, onTextChange = onDraftChange, onAdd = onAddTask)

        // TODO 6 — Show every task in uiState.tasks
        uiState.tasks.forEach { task -> Text("• $task") }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyPlannerScreenPreview() {
    StudyPlannerScreen(
        uiState = StudyPlannerUiState(tasks = listOf("Read Ch. 2", "Finish Lab 6")),
        draft = "",
        onDraftChange = {},
        onAddTask = {}
    )
}