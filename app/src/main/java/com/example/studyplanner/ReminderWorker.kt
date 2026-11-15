package com.example.studyplanner

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

// WHAT to do: count unfinished tasks and remind the user
class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val database = StudyDatabase.getDatabase(applicationContext)
        val unfinished = database.studyTaskDao().countUnfinished()
        if (unfinished > 0) {
            showReminder(applicationContext, unfinished)
        }
        return Result.success()
    }
}

// WHEN to do it: about once a day, when the battery is not low
fun scheduleDailyReminder(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()
    val request =
        PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "daily_reminder",                 // a name for the work
        ExistingPeriodicWorkPolicy.KEEP,  // already there? keep it
        request
    )
}
