package com.example.studyplanner

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyTaskDao {

    @Query("SELECT * FROM study_tasks ORDER BY id")
    fun getAll(): Flow<List<StudyTask>>

    @Insert
    suspend fun insert(task: StudyTask)

    @Update
    suspend fun update(task: StudyTask)

    @Query("DELETE FROM study_tasks WHERE is_done = 1")
    suspend fun deleteDone()

    // Read once (not watched): how many tasks are not done?   // new (Lecture 12)
    @Query("SELECT COUNT(*) FROM study_tasks WHERE is_done = 0")
    suspend fun countUnfinished(): Int
}
