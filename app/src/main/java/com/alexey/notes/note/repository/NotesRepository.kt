package com.alexey.notes.note.repository

import com.alexey.notes.notes_list.recycler.Note

interface NotesRepository {

    fun loadNote(id: Long) : Note

    fun addNote(title: String, text: String): Long
    fun updateNote(id: Long, title: String, text: String): Boolean
    fun deleteNote(id: Long)
}