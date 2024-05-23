package com.alexey.notes.network

import com.alexey.notes.notes_list.recycler.Note
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Интерактор для загрузки заметки
 */
class NoteInteractor  {

    fun fetchNote(): Call<Note> = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build().create(NoteApi::class.java).fetchNote()

    companion object {
        private const val BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/bsc-internship-notes.appspot.com/o/"
    }
}