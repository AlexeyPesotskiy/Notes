package com.alexey.notes.notes_list.repository

import com.alexey.notes.db.AppDataBase
import com.alexey.notes.notes_list.recycler.Note

class NotesRepositoryImpl(private val dataBase: AppDataBase) : NotesRepository {

    /**
     * Загрузить все заметки из [AppDataBase]
     */
    override fun loadData(): List<Note> = dataBase.noteDao().getAll()

    /**
     * Сохранить новую заметку в [AppDataBase]
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun addNote(title: String, text: String): Long =
        dataBase.noteDao().insert(Note(title = title, text = text))
}