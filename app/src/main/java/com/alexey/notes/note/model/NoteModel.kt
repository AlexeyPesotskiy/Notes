package com.alexey.notes.note.model

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
    override fun saveNote(title: String, text: String): Boolean {
        return true
    }
}