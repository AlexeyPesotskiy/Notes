package com.alexey.notes.model

class NoteModel : Model {

    override fun saveNote(title: String, text: String): Boolean {
        return true
    }
}