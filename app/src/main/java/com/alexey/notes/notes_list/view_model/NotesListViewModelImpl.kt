package com.alexey.notes.notes_list.view_model

import androidx.lifecycle.ViewModel
import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.network.NoteInteractor
import com.alexey.notes.notes_list.recycler.Note
import com.alexey.notes.notes_list.repository.NotesRepository
import com.alexey.notes.notes_list.view.NotesListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel для [NotesListView] и [NotesRepository]
 *
 * @param repository репозиторий к которому имеем доступ через интерфейс
 */
class NotesListViewModelImpl(private val repository: NotesRepository) : ViewModel(),
    NotesListViewModel {

    private lateinit var noteList: List<Note>

    /**
     * Обновление списка
     */
    override fun updateList() {
        noteList = repository.loadData()
        onUpdateNotesEvent.value = noteList
    }

    /**
     * Обновление списка при поиске
     *
     * @param searchText текст, введённый в строку поиска
     */
    override fun updateListOnSearch(searchText: String) {
        onUpdateNotesEvent.value = noteList.filter {
            it.title.contains(searchText) || it.text.contains(searchText)
        }
    }

    /**
     * Обработка нажатия на кнопку "Скачать заметку"
     */
    override fun downloadBtnClicked() {
        NoteInteractor().fetchNote().enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                val title = response.body()?.title.orEmpty()
                val text = response.body()?.text.orEmpty()

                if (text.isEmpty() || title.isEmpty())
                    onDownloadFailedEvent.call()
                else {
                    repository.addNote(title, text)
                    updateList()
                    onDownloadSuccessEvent.call()
                }
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                onDownloadFailedEvent.call()
            }
        })
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
     * Обновление списка в RecyclerView
     */
    override val onUpdateNotesEvent = SingleLiveEvent<List<Note>>()

    /**
     * Удалось скачать заметку
     */
    override val onDownloadSuccessEvent = SingleLiveEvent<Unit>()

    /**
     * Не удалось скачать заметку
     */
    override val onDownloadFailedEvent = SingleLiveEvent<Unit>()

    /**
     * Нажатие на кнопку "О приложении"
     */
    override val onAboutBtnClickedEvent = SingleLiveEvent<Unit>()

    /**
     * Нажатие на кнопку "Добавить заметку"
     */
    override val onAddNoteBtnClicked = SingleLiveEvent<Unit>()
}