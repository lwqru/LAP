package sa.edu.iau.cshj.studyplanner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme

// Immutable screen state: all fields have defaults, as required by Lab 4.
data class PlannerUiState(
    val greeting: String = "Hello, Aisha!",
    val totalTasks: Int = 7,
    val doneTasks: Int = 0
)

// Stateless screen: state comes in through uiState and the action goes out via a lambda.
@Composable
fun PlannerHomeScreen(
    uiState: PlannerUiState,
    onMarkTaskDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(uiState.greeting, style = MaterialTheme.typography.headlineMedium)
        Text("Study Planner", style = MaterialTheme.typography.titleLarge)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Study progress", style = MaterialTheme.typography.titleMedium)
                Text("${uiState.doneTasks} of ${uiState.totalTasks} done")
                Button(
                    onClick = onMarkTaskDone,
                    enabled = uiState.doneTasks < uiState.totalTasks,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (uiState.doneTasks < uiState.totalTasks) "Mark one task done" else "All tasks completed")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Nothing done yet")
@Composable
private fun PlannerHomePreviewNotStarted() {
    StudyPlannerTheme {
        PlannerHomeScreen(
            uiState = PlannerUiState(greeting = "Hello, Aisha!", totalTasks = 7, doneTasks = 0),
            onMarkTaskDone = {}
        )
    }
}

@Preview(showBackground = true, name = "Some tasks completed")
@Composable
private fun PlannerHomePreviewInProgress() {
    StudyPlannerTheme {
        PlannerHomeScreen(
            uiState = PlannerUiState(greeting = "Welcome back, Aisha!", totalTasks = 7, doneTasks = 4),
            onMarkTaskDone = {}
        )
    }
}
