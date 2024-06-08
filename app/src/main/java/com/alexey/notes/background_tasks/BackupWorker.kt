package com.alexey.notes.background_tasks

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.notes_list.repository.NotesRepositoryImpl

class BackupWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val repository = NotesRepositoryImpl(AppDataBase.getDataBase(context))

    override fun doWork(): Result {
        Log.d(TAG, "Сохранено ${repository.loadData().size} заметок")
        return Result.success()
    }

    companion object {
        const val TAG = "BACKUP_NOTES"
    }
}