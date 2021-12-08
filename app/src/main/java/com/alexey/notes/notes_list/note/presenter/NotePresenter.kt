package com.alexey.notes.notes_list.note.presenter

import com.alexey.notes.notes_list.note.model.Model
import com.alexey.notes.notes_list.note.view.NoteView

/**
 * Презентер для [NoteView] и [Model]
 *
 * @param model модель, к которой имеем доступ через интерфейс
 */
class NotePresenter(private var model: Model) : Presenter {

    private lateinit var view: NoteView

    var title: String = ""
    var text: String = ""

    /**
     * Инициализация
     *
     * @param view вью, к которой имеем доступ через интерфейс
     */
    override fun attachView(view: NoteView) {
        this.view = view
    }

    override fun init(title: String, text: String) {
        this.title = title
        this.text = text
        view.fillLayout(this.title, this.text)
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
            model.saveNote(title, text) -> view.onSaveSuccessEvent()
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