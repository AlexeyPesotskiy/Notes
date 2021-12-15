package com.alexey.notes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alexey.notes.notes_list.recycler.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM Notes")
    fun getAll(): List<Note>

    @Query("SELECT * FROM NOTES WHERE id LIKE :id LIMIT 1")
    fun findNoteById(id: Long): Note

    @Insert
    fun insert(note: Note): Long

    @Update
    fun update(note: Note)
}