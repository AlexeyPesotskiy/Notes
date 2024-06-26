package com.alexey.notes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexey.notes.notes_list.recycler.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null
        private const val DB_NAME = "Notes"

        fun getDataBase(context: Context): AppDataBase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DB_NAME
                ).allowMainThreadQueries().build()
                INSTANCE = instance

                instance
            }
    }
}