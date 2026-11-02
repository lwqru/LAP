package sa.edu.iau.cshj.studyplanner.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

// Saves the progress so it is kept when the user opens a task and comes back.
private val PlannerUiStateSaver = Saver<PlannerUiState, Int>(
    save = { it.doneTasks },
    restore = { PlannerUiState(doneTasks = it) }
)

// The Route owns state and creates a new immutable state with copy() when the button is tapped.
@Composable
fun PlannerHomeRoute(onTaskClick: (String) -> Unit) {
    var uiState by rememberSaveable(stateSaver = PlannerUiStateSaver) {
        mutableStateOf(PlannerUiState())
    }

    PlannerHomeScreen(
        uiState = uiState,
        onMarkTaskDone = {
            if (uiState.doneTasks < uiState.totalTasks) {
                uiState = uiState.copy(doneTasks = uiState.doneTasks + 1)
            }
        },
        onTaskClick = onTaskClick
    )
}
