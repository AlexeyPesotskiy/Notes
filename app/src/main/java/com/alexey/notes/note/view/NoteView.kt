package com.alexey.notes.note.view

interface NoteView {

    fun continueSave()

    fun shareNote(title: String, text: String)
}