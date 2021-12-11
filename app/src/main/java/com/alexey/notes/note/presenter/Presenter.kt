package com.alexey.notes.note.presenter

import com.alexey.notes.note.view.NoteView

interface Presenter {

    fun attachView(view: NoteView)

    fun init(title: String, text: String)

    fun save(title: String, text: String)
    fun shareBtnClicked(title: String, text: String)
    fun backBtnClicked()
}