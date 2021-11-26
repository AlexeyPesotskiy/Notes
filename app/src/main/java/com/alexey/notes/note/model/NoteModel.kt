package com.alexey.notes.note.model

class NoteModel : Model {

    override fun saveNote(title: String, text: String): Boolean {
        return true
    }
}