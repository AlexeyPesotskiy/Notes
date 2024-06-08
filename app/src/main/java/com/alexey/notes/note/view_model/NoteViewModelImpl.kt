package com.alexey.notes.note.view_model

import androidx.lifecycle.ViewModel
import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.note.repository.NotesRepository
import com.alexey.notes.note.view.NoteView
import com.alexey.notes.notes_list.recycler.Note

/**
 * ViewModel для [NoteView] и [NotesRepository]
 *
 * @param repository репозиторий к которому имеем доступ через интерфейс
 */
class NoteViewModelImpl(private val repository: NotesRepository) : ViewModel(), NoteViewModel {

    var id = 0L
    override var title = ""
    override var text = ""

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
                onSaveSuccessEvent.value = Note(id, title, text)
            }
            repository.updateNote(id, title, text) ->
                onSaveSuccessEvent.value = Note(id, title, text)
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
     * Обработка нажатия на кнопку "Удалить"
     */
    override fun deleteBtnClicked() {
        if (id > 0L) {
            repository.deleteNote(id)
            onDeleteSuccessEvent.call()
        } else
            onDeleteFailedEvent.call()
        onBackEvent.call()
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
    override val onSaveSuccessEvent = SingleLiveEvent<Note>()

    /**
     * Попытка сохранить пустую заметку
     */
    override val onAttemptSaveEmptyContent = SingleLiveEvent<Unit>()

    /**
     * Не удалось сохранить заметку
     */
    override val onSaveFailedEvent = SingleLiveEvent<Unit>()


    /**
     * Поделиться заметкой
     */
    override val onShareEvent = SingleLiveEvent<Note>()

    /**
     * Попытка поделиться пустой заметкой
     */
    override val onAttemptShareEmptyContent = SingleLiveEvent<Unit>()


    /**
     * Удалось удалить заметку
     */
    override val onDeleteSuccessEvent = SingleLiveEvent<Unit>()

    /**
     * Не удалось удалить заметку
     */
    override val onDeleteFailedEvent = SingleLiveEvent<Unit>()


    /**
     * Нажатие на кнопку назад
     */
    override val onBackEvent = SingleLiveEvent<Unit>()
}