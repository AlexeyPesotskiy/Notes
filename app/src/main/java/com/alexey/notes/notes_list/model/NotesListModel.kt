package com.alexey.notes.notes_list.model

import com.alexey.notes.notes_list.recycler.Note

class NotesListModel : Model {

    private var notesList: ArrayList<Note> = arrayListOf(
        Note("Заметка1", "текст1"),
        Note("Заметка2", "текст2"),
        Note("Заметка3", "текст3")
    )

    override fun loadData(): ArrayList<Note> = notesList
}