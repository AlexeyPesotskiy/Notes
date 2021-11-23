package com.alexey.notes.model

interface Model {

    fun saveNote(title: String, text: String): Boolean
}