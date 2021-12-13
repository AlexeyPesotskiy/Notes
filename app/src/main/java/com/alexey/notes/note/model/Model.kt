package com.alexey.notes.note.model

import com.alexey.notes.notes_list.recycler.Note

interface Model {

    fun loadNote(id: Long) : Note

    fun addNote(title: String, text: String): Long
    fun updateNote(id: Long, title: String, text: String): Boolean
}