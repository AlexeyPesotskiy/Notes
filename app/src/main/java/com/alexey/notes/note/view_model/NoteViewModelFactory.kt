package com.alexey.notes.note.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexey.notes.note.repository.NotesRepository

class NoteViewModelFactory(private var repository: NotesRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NoteViewModelImpl(repository) as T
}