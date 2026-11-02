package sa.edu.iau.cshj.studyplanner.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme

// P4.3 — one reusable, stateless row: data in, events out, modifier last.
@Composable
fun TaskItem(
    task: StudyTask,
    onTaskClick: (Int) -> Unit,
    onDoneChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task.id) }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { checked -> onDoneChange(task.id, checked) }
            )
            Column(modifier = Modifier.padding(start = 4.dp)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text(task.course, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// P4.6 — Preview 1: a task that is not done.
@Preview(showBackground = true, name = "TaskItem - not done")
@Composable
private fun TaskItemNotDonePreview() {
    StudyPlannerTheme {
        TaskItem(
            task = StudyTask(id = 1, title = "Complete Lab 7", course = "CSC 402", isDone = false),
            onTaskClick = {},
            onDoneChange = { _, _ -> }
        )
    }
}

// P4.6 — Preview 2: a task that is done.
@Preview(showBackground = true, name = "TaskItem - done")
@Composable
private fun TaskItemDonePreview() {
    StudyPlannerTheme {
        TaskItem(
            task = StudyTask(id = 2, title = "Review Kotlin Notes", course = "CSC 402", isDone = true),
            onTaskClick = {},
            onDoneChange = { _, _ -> }
        )
    }
}
