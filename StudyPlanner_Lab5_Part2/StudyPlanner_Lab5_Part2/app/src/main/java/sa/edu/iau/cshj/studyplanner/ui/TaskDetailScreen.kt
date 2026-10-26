package sa.edu.iau.cshj.studyplanner.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme

data class TaskDetailUiState(
    val title: String = "Complete CSC 402 Lab 5"
)

@Composable
fun TaskDetailScreen(
    uiState: TaskDetailUiState,
    onShareClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Task Details", style = MaterialTheme.typography.headlineMedium)
        Text(uiState.title, style = MaterialTheme.typography.titleLarge)
        Button(onClick = onShareClick) {
            Text("Share")
        }
        OutlinedButton(onClick = onBackClick) {
            Text("Back")
        }
    }
}

@Composable
fun TaskDetailRoute(
    title: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    TaskDetailScreen(
        uiState = TaskDetailUiState(title = title),
        onShareClick = {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, "Today I'm studying: $title")
                type = "text/plain"
            }
            context.startActivity(
                Intent.createChooser(sendIntent, "Share task via")
            )
        },
        onBackClick = onBackClick
    )
}

@Preview(showBackground = true, name = "Task detail - Lab 5")
@Composable
private fun TaskDetailPreviewLab() {
    StudyPlannerTheme {
        TaskDetailScreen(
            uiState = TaskDetailUiState("Complete CSC 402 Lab 5"),
            onShareClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Task detail - Quiz")
@Composable
private fun TaskDetailPreviewQuiz() {
    StudyPlannerTheme {
        TaskDetailScreen(
            uiState = TaskDetailUiState("Prepare for Quiz"),
            onShareClick = {},
            onBackClick = {}
        )
    }
}
