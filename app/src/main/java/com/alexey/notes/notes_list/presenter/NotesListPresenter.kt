package com.alexey.notes.notes_list.presenter

import com.alexey.notes.notes_list.repository.NotesRepository
import com.alexey.notes.notes_list.recycler.Note
import com.alexey.notes.notes_list.view.NotesListView

class NotesListPresenter(private var repository: NotesRepository) : Presenter {

    private lateinit var view: NotesListView
    private lateinit var noteList: List<Note>

    /**
     * Заполнение списка начальными данными
     */
    override fun initList() {
        noteList = repository.loadData()
        for (note in noteList)
            view.addNote(note)
    }

    /**
     * Обновление списка
     */
    override fun updateList() {
        val updatedList = repository.loadData()

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