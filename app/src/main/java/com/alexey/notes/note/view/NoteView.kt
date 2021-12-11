package com.alexey.notes.note.view

interface NoteView {

    fun fillLayout(title: String, text: String)

    fun onSaveSuccessEvent()
    fun onSaveFailedEvent()
    fun onAttemptSaveEmptyContent()

    fun shareNote(title: String, text: String)
    fun onAttemptShareEmptyContent()

    fun onBackEvent()
}