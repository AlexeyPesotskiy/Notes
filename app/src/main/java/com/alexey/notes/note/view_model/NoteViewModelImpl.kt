package com.alexey.notes.note.view_model

import androidx.lifecycle.ViewModel
import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.note.repository.NotesRepository
import com.alexey.notes.note.view.NoteView
import com.alexey.notes.notes_list.recycler.Note

/**
 * NoteViewModel для [NoteView] и [NotesRepository]
 */
class NoteViewModelImpl : ViewModel(), NoteViewModel {

    private lateinit var repository: NotesRepository

    var id: Long = 0
    var title = ""
    var text = ""

    /**
     * Инициализация
     *
     * @param repository репозиторий к которому имеем доступ через интерфейс
     */
    override fun attachRepository(repository: NotesRepository) {
        this.repository = repository
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
                title = it.title
                text = it.text
            }
    }

    /**
     * Обработка нажатия на кнопку "Сохранить"
     */
    override fun save() {
        when {
            title.isEmpty() || text.isEmpty() -> onAttemptSaveEmptyContent.call()
            id == 0L -> {
                id = repository.addNote(title, text)
                onSaveSuccessEvent.call()
            }
            repository.updateNote(id, title, text) -> onSaveSuccessEvent.call()
            else -> onSaveFailedEvent.call()
        }
    }

    /**
     * Обработка нажатия на кнопку "Поделиться"
     */
    override fun shareBtnClicked() {
        if (title.isEmpty() || text.isEmpty())
            onAttemptShareEmptyContent.call()
        else
            onShareEvent.value = Note(title = title, text = text)
    }

    /**
     * Обработка нажатия на кнопку "Назад"
     */
    override fun backBtnClicked() {
        onBackEvent.call()
    }

    /**
     * Удалось сохранить заметку
     */
    val onSaveSuccessEvent = SingleLiveEvent<Unit>()

    /**
     * Попытка сохранить пустую заметку
     */
    val onAttemptSaveEmptyContent = SingleLiveEvent<Unit>()

    /**
     * Не удалось сохранить заметку
     */
    val onSaveFailedEvent = SingleLiveEvent<Unit>()


    /**
     * Поделиться заметкой
     */
    val onShareEvent = SingleLiveEvent<Note>()

    /**
     * Попытка поделиться пустой заметкой
     */
    val onAttemptShareEmptyContent = SingleLiveEvent<Unit>()


    /**
     * Нажатие на кнопку назад
     */
    val onBackEvent = SingleLiveEvent<Unit>()
}