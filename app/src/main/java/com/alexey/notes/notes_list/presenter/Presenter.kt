package com.alexey.notes.notes_list.presenter

import com.alexey.notes.notes_list.view.NotesListView

interface Presenter {

    fun initList()
    fun updateList()

    fun attachView(view: NotesListView)

    fun aboutBtnClicked()
    fun addNoteBtnClicked()
}