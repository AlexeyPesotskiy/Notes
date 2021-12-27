package com.alexey.notes.notes_list.view_model

import androidx.lifecycle.ViewModel
import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.notes_list.repository.NotesRepository
import com.alexey.notes.notes_list.recycler.Note

class NotesListViewModelImpl : ViewModel(), NotesListViewModel {

    private lateinit var noteList: List<Note>
    private lateinit var repository: NotesRepository

    /**
     * Заполнение списка начальными данными
     */
    override fun initList() {
        noteList = repository.loadData()
        onAddNoteEvent.value = noteList
    }

    /**
     * Обновление списка
     */
    override fun updateList() {
        val updatedList = repository.loadData()

        for (note in updatedList)
            if (!noteList.contains(note))
                onUpdateNoteEvent.value = note

        noteList = updatedList
    }

    /**
     * Инициализация
     *
     * @param repository репозиторий к которому имеем доступ через интерфейс
     */
    override fun attachRepository(repository: NotesRepository) {
        this.repository = repository
    }

    /**
     * Обработка нажатия на кнопку "О приложении"
     */
    override fun aboutBtnClicked() {
        onAboutBtnClickedEvent.call()
    }

    /**
     * Обработка нажатия на кнопку "Добавить заметку"
     */
    override fun addNoteBtnClicked() {
        onAddNoteBtnClicked.call()
    }

    /**
     * Добавление заметки в RecyclerView
     */
    val onAddNoteEvent = SingleLiveEvent<List<Note>>()

    /**
     * Обновление заметки в RecyclerView
     */
    val onUpdateNoteEvent = SingleLiveEvent<Note>()

    /**
     * Нажатие на кнопку "О приложении"
     */
    val onAboutBtnClickedEvent = SingleLiveEvent<Unit>()

    /**
     * Нажатие на кнопку "Добавить заметку"
     */
    val onAddNoteBtnClicked = SingleLiveEvent<Unit>()
}