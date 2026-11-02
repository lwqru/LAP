package sa.edu.iau.cshj.studyplanner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme

// P4.2 — the list is the ONE source of truth. Progress is computed from it, never stored.
data class PlannerUiState(
    val greeting: String = "Hello, Aisha!",
    val tasks: List<StudyTask> = emptyList()
) {
    val totalTasks: Int get() = tasks.size
    val doneTasks: Int get() = tasks.count { it.isDone }
}

// Stateless screen: state in, events out as lambdas, modifier last.
@Composable
fun PlannerHomeScreen(
    uiState: PlannerUiState,
    onTaskClick: (Int) -> Unit,
    onDoneChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(uiState.greeting, style = MaterialTheme.typography.headlineMedium)
        Text(
            "Study progress: ${uiState.doneTasks} of ${uiState.totalTasks} done",
            style = MaterialTheme.typography.titleMedium
        )

        // P4.4 — LazyColumn with items and a key.
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(uiState.tasks, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = onTaskClick,
                    onDoneChange = onDoneChange
                )
            }
        }
    }
}

// P4.6 — Preview 3: the home screen with five tasks.
@Preview(showBackground = true, name = "Home - five tasks")
@Composable
private fun PlannerHomeScreenPreview() {
    StudyPlannerTheme {
        PlannerHomeScreen(
            uiState = PlannerUiState(
                tasks = sampleTasks.map { if (it.id <= 2) it.copy(isDone = true) else it }
            ),
            onTaskClick = {},
            onDoneChange = { _, _ -> }
        )
    }
}
