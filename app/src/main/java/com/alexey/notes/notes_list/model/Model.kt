package com.alexey.notes.notes_list.model

import com.alexey.notes.notes_list.recycler.Note

interface Model {
    fun loadData() : ArrayList<Note>
}