package com.alexey.notes.notes_list.note.model

/**
 * Модель для NotePresenter
 */
class NoteModel : Model {

    /**
     * Сохранить заметку (нет реализаци)
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun saveNote(title: String, text: String): Boolean = true
}