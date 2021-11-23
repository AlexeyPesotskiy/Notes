package com.alexey.notes.presenter

import com.alexey.notes.view.NoteView

interface Presenter {

    fun attachView(view: NoteView)

    fun save(title: String, text: String)
}