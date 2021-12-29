package com.alexey.notes.notes_list.view_model

import androidx.lifecycle.ViewModel
import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.notes_list.repository.NotesRepository
import com.alexey.notes.notes_list.view.NotesListView
import com.alexey.notes.notes_list.recycler.Note

/**
 * ViewModel для [NotesListView] и [NotesRepository]
 *
 * @param repository репозиторий к которому имеем доступ через интерфейс
 */
class NotesListViewModelImpl(private val repository: NotesRepository)
    : ViewModel(), NotesListViewModel {

    private lateinit var noteList: List<Note>

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
    override val onAddNoteEvent = SingleLiveEvent<List<Note>>()

    /**
     * Обновление заметки в RecyclerView
     */
    override val onUpdateNoteEvent = SingleLiveEvent<Note>()

    /**
     * Нажатие на кнопку "О приложении"
     */
    override val onAboutBtnClickedEvent = SingleLiveEvent<Unit>()

    /**
     * Нажатие на кнопку "Добавить заметку"
     */
    override val onAddNoteBtnClicked = SingleLiveEvent<Unit>()
}