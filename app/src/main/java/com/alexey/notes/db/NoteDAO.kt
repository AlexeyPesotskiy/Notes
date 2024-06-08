package com.alexey.notes.db

import androidx.room.*
import com.alexey.notes.notes_list.recycler.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM Notes")
    fun getAll(): List<Note>

    @Query("SELECT * FROM NOTES WHERE id LIKE :id LIMIT 1")
    fun findNoteById(id: Long): Note

    @Query("DELETE FROM NOTES WHERE id LIKE :id")
    fun delete(id: Long)

    @Insert
    fun insert(note: Note): Long

    @Update
    fun update(note: Note)
}