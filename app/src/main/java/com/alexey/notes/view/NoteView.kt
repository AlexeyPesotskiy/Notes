package com.alexey.notes.view

interface NoteView {

    fun onSaveSuccessEvent()

    fun onSaveFailedEvent()

    fun onAttemptSaveEmptyContent()
}