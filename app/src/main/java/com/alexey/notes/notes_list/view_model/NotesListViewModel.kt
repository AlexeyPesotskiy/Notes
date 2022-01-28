package com.alexey.notes.notes_list.view_model

import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.notes_list.recycler.Note

interface NotesListViewModel {

    fun updateList()
    fun updateListOnSearch(searchText: String)

    fun downloadBtnClicked()
    fun aboutBtnClicked()
    fun addNoteBtnClicked()

    val onUpdateNotesEvent: SingleLiveEvent<List<Note>>

    val onDownloadSuccessEvent: SingleLiveEvent<Unit>
    val onDownloadFailedEvent: SingleLiveEvent<Unit>
    val onAboutBtnClickedEvent: SingleLiveEvent<Unit>
    val onAddNoteBtnClicked: SingleLiveEvent<Unit>
}