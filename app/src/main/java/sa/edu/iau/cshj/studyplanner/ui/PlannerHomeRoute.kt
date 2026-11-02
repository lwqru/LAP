package sa.edu.iau.cshj.studyplanner.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// Same saving approach as Part 3 (rememberSaveable). We save only the ids of the
// done tasks, then rebuild the list from the sample tasks when restoring.
private val PlannerUiStateSaver = listSaver<PlannerUiState, Int>(
    save = { state -> state.tasks.filter { it.isDone }.map { it.id } },
    restore = { doneIds ->
        PlannerUiState(tasks = sampleTasks.map { it.copy(isDone = it.id in doneIds) })
    }
)

// The Route OWNS the state. TaskItem and PlannerHomeScreen hold none.
@Composable
fun PlannerHomeRoute(onTaskClick: (Int) -> Unit) {
    var uiState by rememberSaveable(stateSaver = PlannerUiStateSaver) {
        mutableStateOf(PlannerUiState(tasks = sampleTasks))
    }

    PlannerHomeScreen(
        uiState = uiState,
        onTaskClick = onTaskClick,                 // sent UP to the NavHost
        onDoneChange = { id, done ->
            // P4.5 — a NEW list made with map + copy; the old task is never edited.
            uiState = uiState.copy(
                tasks = uiState.tasks.map { if (it.id == id) it.copy(isDone = done) else it }
            )
        }
    )
}
