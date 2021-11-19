package com.alexey.notes.presenter

import com.alexey.notes.model.NoteModel
import com.alexey.notes.view.NoteView

class NotePresenter(private var model: NoteModel) {

    private lateinit var view: NoteView

    fun attachView(view: NoteView) {
        this.view = view
    }

    fun viewIsReady() {}

    fun save(title: String, text: String) {
        if (title.isEmpty() || text.isEmpty())
            view.onAttemptSaveEmptyContent()
        else if (model.saveNote(title, text))
            view.onSaveSuccessEvent()
        else
            view.onSaveFailedEvent()
    }

}