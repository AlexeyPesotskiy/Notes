package com.alexey.notes.presenter

import com.alexey.notes.R
import com.alexey.notes.model.MainModel
import com.alexey.notes.view.MainActivity

class MainPresenter(private var model: MainModel) {

    private lateinit var view: MainActivity

    fun attachView(view: MainActivity) {
        this.view = view
    }

    fun viewIsReady() {}

    fun save() {
        if (model.saveNote())
            view.showToast(R.string.note_saved)
    }

}