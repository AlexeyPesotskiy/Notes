package com.alexey.notes.notes_list.view

import com.alexey.notes.notes_list.recycler.Note

interface NotesListView {

    fun addNote(note: Note)

    fun openAboutScreen()
}