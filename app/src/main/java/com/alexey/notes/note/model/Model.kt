package com.alexey.notes.note.model

interface Model {

    fun saveNote(title: String, text: String): Boolean
}