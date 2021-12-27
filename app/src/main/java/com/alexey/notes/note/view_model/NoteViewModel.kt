package com.alexey.notes.note.view_model

import com.alexey.notes.note.repository.NotesRepository

interface NoteViewModel {

    fun attachRepository(repository: NotesRepository)

    fun init(id: Long)

    fun save()
    fun shareBtnClicked()
    fun backBtnClicked()
}