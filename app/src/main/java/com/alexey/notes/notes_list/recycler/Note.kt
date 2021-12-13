package com.alexey.notes.notes_list.recycler

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_title") val title: String,
    @ColumnInfo(name = "note_text") val text: String
    ) : Parcelable
