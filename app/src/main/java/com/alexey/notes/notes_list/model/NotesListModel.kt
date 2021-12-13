package com.alexey.notes.notes_list.model

import com.alexey.notes.db.AppDataBase
import com.alexey.notes.notes_list.recycler.Note

class NotesListModel(private val dataBase: AppDataBase) : Model {

    /**
     * Загрузить все заметки из [AppDataBase]
     */
    override fun loadData(): List<Note> = dataBase.noteDao().getAll()
}