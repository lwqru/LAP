package com.example.studyplanner

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// @Dao = the queries for the table
@Dao
interface StudyTaskDao {

    // Read: returns a Flow, keeps watching the table (we write the SQL)
    @Query("SELECT * FROM study_tasks ORDER BY id")
    fun getAll(): Flow<List<StudyTask>>

    // Writes: suspend, run once (Room writes the SQL)
    @Insert
    suspend fun insert(task: StudyTask)

    @Update
    suspend fun update(task: StudyTask)

    // Challenge: delete many rows -> we write the SQL with @Query
    @Query("DELETE FROM study_tasks WHERE is_done = 1")
    suspend fun deleteDone()
}