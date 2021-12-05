package com.alexey.notes.notes_list.presenter

import com.alexey.notes.notes_list.model.Model
import com.alexey.notes.notes_list.view.NotesListView

class NotesListPresenter(private var model: Model) : Presenter {

    private lateinit var view: NotesListView

    /**
     * Заполнение списка начальными данными
     */
    override fun initList() {
        val noteList = model.loadData()
        for (note in noteList)
            view.addNote(note)
    }
    /**
     * Инициализация
     *
     * @param view вью, к которой имеем доступ через интерфейс
     */
    override fun attachView(view: NotesListView) {
        this.view = view
    }

    /**
     * Обработка нажатия на кнопку "О приложении"
     */
    override fun aboutBtnClicked() {
        view.openAboutScreen()
    }
}