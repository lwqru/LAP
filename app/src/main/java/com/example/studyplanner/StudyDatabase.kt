package com.example.studyplanner

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// @Database = lists the tables and gives the DAO
@Database(entities = [StudyTask::class], version = 1, exportSchema = false)
abstract class StudyDatabase : RoomDatabase() {

    abstract fun studyTaskDao(): StudyTaskDao

    companion object {
        @Volatile
        private var INSTANCE: StudyDatabase? = null

        // One shared database for the whole app (singleton)
        fun getDatabase(context: Context): StudyDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    StudyDatabase::class.java,
                    "study_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}