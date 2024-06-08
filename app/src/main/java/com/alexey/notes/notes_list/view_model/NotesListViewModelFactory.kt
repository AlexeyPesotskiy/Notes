package com.alexey.notes.notes_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexey.notes.notes_list.repository.NotesRepository

class NotesListViewModelFactory(private var repository: NotesRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NotesListViewModelImpl(repository) as T
}