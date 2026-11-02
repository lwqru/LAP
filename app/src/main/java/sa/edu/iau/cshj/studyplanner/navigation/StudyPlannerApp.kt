package sa.edu.iau.cshj.studyplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import sa.edu.iau.cshj.studyplanner.ui.PlannerHomeRoute
import sa.edu.iau.cshj.studyplanner.ui.TaskDetailRoute

// Only the NavHost navigates.
@Composable
fun StudyPlannerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PlannerHome
    ) {
        composable<PlannerHome> {
            PlannerHomeRoute(
                onTaskClick = { id -> navController.navigate(TaskDetail(taskId = id)) }
            )
        }

        composable<TaskDetail> { backStackEntry ->
            val detail: TaskDetail = backStackEntry.toRoute()
            TaskDetailRoute(
                taskId = detail.taskId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
