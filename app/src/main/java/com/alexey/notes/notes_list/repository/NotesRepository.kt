package com.alexey.notes.notes_list.repository

import com.alexey.notes.notes_list.recycler.Note

interface NotesRepository {

    fun loadData() : List<Note>
}