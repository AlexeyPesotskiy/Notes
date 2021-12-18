package com.alexey.notes.note.presenter

import com.alexey.notes.note.repository.NotesRepository
import com.alexey.notes.note.view.NoteView

/**
 * Презентер для [NoteView] и [NotesRepository]
 *
 * @param notesRepository репозиторий, к которой имеем доступ через интерфейс
 */
class NotePresenter(private var repository: NotesRepository) : Presenter {

    private lateinit var view: NoteView

    var id: Long = 0

    /**
     * Инициализация
     *
     * @param view вью, к которой имеем доступ через интерфейс
     */
    override fun attachView(view: NoteView) {
        this.view = view
    }

    /**Инициализация
     *
     * Заполняем открытую заметку данными
     * @param id id текущей заметки
     */
    override fun init(id: Long) {
        this.id = id

        if (id != 0L)
            repository.loadNote(id).let {
                view.fillLayout(it.title, it.text)
            }
        else
            view.fillLayout("", "")
    }

    /**
     * Обработка нажатия на кнопку "Сохранить"
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun save(title: String, text: String) {
        when {
            title.isEmpty() || text.isEmpty() -> view.onAttemptSaveEmptyContent()
            id == 0L -> {
                id = repository.addNote(title, text)
                view.onSaveSuccessEvent()
            }
            repository.updateNote(id, title, text) -> view.onSaveSuccessEvent()
            else -> view.onSaveFailedEvent()
        }
    }

    /**
     * Обработка нажатия на кнопку "Поделиться"
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun shareBtnClicked(title: String, text: String) {
        if (title.isEmpty() || text.isEmpty())
            view.onAttemptShareEmptyContent()
        else
            view.shareNote(title, text)
    }

    /**
     * Обработка нажатия на кнопку "Назад"
     */
    override fun backBtnClicked() {
        view.onBackEvent()
    }
}