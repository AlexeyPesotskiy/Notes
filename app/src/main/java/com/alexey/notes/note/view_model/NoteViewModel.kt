package com.alexey.notes.note.view_model

import com.alexey.notes.arch.SingleLiveEvent
import com.alexey.notes.notes_list.recycler.Note

interface NoteViewModel {

    var title: String
    var text: String

    fun init(id: Long)

    fun save()
    fun shareBtnClicked()
    fun deleteBtnClicked()
    fun backBtnClicked()

    val onSaveSuccessEvent: SingleLiveEvent<Note>
    val onAttemptSaveEmptyContent: SingleLiveEvent<Unit>
    val onSaveFailedEvent: SingleLiveEvent<Unit>

    val onShareEvent: SingleLiveEvent<Note>
    val onAttemptShareEmptyContent: SingleLiveEvent<Unit>

    val onDeleteSuccessEvent: SingleLiveEvent<Unit>
    val onDeleteFailedEvent: SingleLiveEvent<Unit>

    val onBackEvent: SingleLiveEvent<Unit>
}