package com.example.studyplanner

import kotlinx.coroutines.flow.Flow

// The ViewModel talks to the repository; the repository talks to the DAO
class TaskRepository(private val dao: StudyTaskDao) {

    val allTasks: Flow<List<StudyTask>> = dao.getAll()

    suspend fun add(title: String) {
        dao.insert(StudyTask(title = title, course = "Mobile Apps"))
    }

    suspend fun setDone(task: StudyTask, done: Boolean) {
        dao.update(task.copy(isDone = done))
    }

    suspend fun deleteDone() {
        dao.deleteDone()
    }
}