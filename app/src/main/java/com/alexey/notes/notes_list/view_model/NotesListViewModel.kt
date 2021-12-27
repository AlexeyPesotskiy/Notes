package com.alexey.notes.notes_list.view_model

import com.alexey.notes.notes_list.repository.NotesRepository

interface NotesListViewModel {

    fun initList()
    fun updateList()

    fun attachRepository(repository: NotesRepository)

    fun aboutBtnClicked()
    fun addNoteBtnClicked()
}