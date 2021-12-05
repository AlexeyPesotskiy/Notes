package com.alexey.notes.notes_list.note.model

interface Model {

    fun saveNote(title: String, text: String): Boolean
}