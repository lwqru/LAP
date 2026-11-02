package sa.edu.iau.cshj.studyplanner.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// The Route owns state and creates a new immutable state with copy() when the button is tapped.
@Composable
fun PlannerHomeRoute() {
    var uiState by remember {
        mutableStateOf(PlannerUiState(greeting = "Hello, Aisha!", totalTasks = 7, doneTasks = 0))
    }

    PlannerHomeScreen(
        uiState = uiState,
        onMarkTaskDone = {
            if (uiState.doneTasks < uiState.totalTasks) {
                uiState = uiState.copy(doneTasks = uiState.doneTasks + 1)
            }
        }
    )
}
