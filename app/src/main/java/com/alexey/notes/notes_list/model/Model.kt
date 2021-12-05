package com.alexey.notes.notes_list.model

import com.alexey.notes.notes_list.Note

interface Model {
    fun loadData() : ArrayList<Note>
}