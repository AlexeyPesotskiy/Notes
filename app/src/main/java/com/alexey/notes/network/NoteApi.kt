package com.alexey.notes.network

import com.alexey.notes.notes_list.recycler.Note
import retrofit2.Call
import retrofit2.http.GET

interface NoteApi {

    @GET("Note.json?alt=media")
    fun fetchNote(): Call<Note>
}