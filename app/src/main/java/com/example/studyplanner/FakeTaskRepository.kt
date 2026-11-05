package com.example.studyplanner

import kotlinx.coroutines.delay

class FakeTaskRepository {
    suspend fun getTodayTasks(): List<StudyTask> {
        delay(1500L) // Experiment 2: 5000L | Experiment 3: Thread.sleep(1500L)
        return listOf(
            StudyTask(1, "Review Lecture 8", "Mobile Apps"),
            StudyTask(2, "Solve lab sheet", "Algorithms"),
            StudyTask(3, "Read chapter 5", "Databases")
        )
    }
}
