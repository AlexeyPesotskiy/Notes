package com.alexey.notes.note.presenter

import com.alexey.notes.note.model.Model
import com.alexey.notes.note.view.NoteView

class NotePresenter(private var model: Model) : Presenter {

    private lateinit var view: NoteView

    override fun attachView(view: NoteView) {
        this.view = view
    }

    override fun save(title: String, text: String) {
        when {
            title.isEmpty() || text.isEmpty() -> view.onAttemptSaveEmptyContent()
            model.saveNote(title, text) -> view.onSaveSuccessEvent()
            else -> view.onSaveFailedEvent()
        }
    }
}