package com.alexey.notes.note.repository

import com.alexey.notes.db.AppDataBase
import com.alexey.notes.notes_list.recycler.Note

/**
 * Репозиторий для NoteViewModelImpl
 */
open class NotesRepositoryImpl(var dataBase: AppDataBase) : NotesRepository {

    /**
     * Сохранить новую заметку в [AppDataBase]
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun addNote(title: String, text: String): Long =
        dataBase.noteDao().insert(Note(title = title, text = text))

    /**
     * Обновить заметку в [AppDataBase]
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

    /**
     * Удалить заметку из [AppDataBase]
     *
     * @param id id заметки в [dataBase]
     */
    override fun deleteNote(id: Long) = dataBase.noteDao().delete(id)
}