package sa.edu.iau.cshj.studyplanner.navigation

import kotlinx.serialization.Serializable

@Serializable
object PlannerHome

// The detail screen now receives the task's id (Part 4).
@Serializable
data class TaskDetail(val taskId: Int)
