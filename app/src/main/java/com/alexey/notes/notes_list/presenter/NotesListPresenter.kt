package com.alexey.notes.notes_list.presenter

import com.alexey.notes.notes_list.model.Model
import com.alexey.notes.notes_list.recycler.Note
import com.alexey.notes.notes_list.view.NotesListView

class NotesListPresenter(private var model: Model) : Presenter {

    private lateinit var view: NotesListView
    private lateinit var noteList: List<Note>

    /**
     * Заполнение списка начальными данными
     */
    override fun initList() {
        noteList = model.loadData()
        for (note in noteList)
            view.addNote(note)
    }

    /**
     * Обновление списка
     */
    override fun updateList() {
        val updatedList = model.loadData()

        for (note in updatedList)
            if (!noteList.contains(note))
                view.updateNote(note)

        noteList = updatedList
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

    /**
     * Обработка нажатия на кнопку "Добавить заметку"
     */
    override fun addNoteBtnClicked() {
        view.openNewNote()
    }
}