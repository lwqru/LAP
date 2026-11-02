package sa.edu.iau.cshj.studyplanner.ui

// P4.2 — one study task. Immutable: to change a task we make a copy().
data class StudyTask(
    val id: Int,
    val title: String,
    val course: String,
    val isDone: Boolean = false
)

// P4.5 — the sample tasks the app starts with (at least five).
val sampleTasks: List<StudyTask> = listOf(
    StudyTask(id = 1, title = "Complete Lab 7", course = "CSC 402"),
    StudyTask(id = 2, title = "Review Kotlin Notes", course = "CSC 402"),
    StudyTask(id = 3, title = "Prepare for Quiz", course = "CSC 305"),
    StudyTask(id = 4, title = "Read Chapter 4", course = "CSC 311"),
    StudyTask(id = 5, title = "Finish Project Report", course = "CSC 498")
)
