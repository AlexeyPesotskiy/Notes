package com.alexey.notes.notes_list.view_model

import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.notes_list.recycler.Note

interface NotesListViewModel {

    fun initList()
    fun updateList()

    fun aboutBtnClicked()
    fun addNoteBtnClicked()

    val onAddNoteEvent: SingleLiveEvent<List<Note>>

    val onUpdateNoteEvent: SingleLiveEvent<Note>

    val onAboutBtnClickedEvent: SingleLiveEvent<Unit>

    val onAddNoteBtnClicked: SingleLiveEvent<Unit>
}