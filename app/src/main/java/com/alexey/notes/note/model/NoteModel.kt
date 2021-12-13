package com.alexey.notes.note.model

import com.alexey.notes.db.AppDataBase
import com.alexey.notes.notes_list.recycler.Note

/**
 * Модель для NotePresenter
 */
class NoteModel(var dataBase: AppDataBase) : Model {

    /**
     * Сохранить новую заметку в [AppDataBase]
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun addNote(title: String, text: String): Long {
        return dataBase.noteDao().insert(Note(title = title, text = text))
    }

    /**
     * Сохранить заметку в [AppDataBase]
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun updateNote(id: Long, title: String, text: String): Boolean {
        dataBase.noteDao().update(Note(id, title = title, text = text))
        return true
    }

    /**
     * Загрузить заметку из [AppDataBase]
     *
     * @param id id заметки в [dataBase]
     */
    override fun loadNote(id: Long): Note = dataBase.noteDao().findNoteById(id)
}