package com.alexey.notes.notes_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexey.notes.R
import com.alexey.notes.databinding.NoteItemBinding

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    interface OnNoteClickListener{
        fun onNoteClick(note: Note, position: Int)
    }

    private lateinit var onClickListener: OnNoteClickListener

    fun setOnNoteClickListener(listener: OnNoteClickListener) {
        onClickListener = listener
    }

    private val noteList = ArrayList<Note>()

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = NoteItemBinding.bind(item)
        fun bind(note: Note) = with(binding) {
            noteTitle.text = note.mTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(noteList[position])
        holder.itemView.setOnClickListener{
            onClickListener.onNoteClick(noteList[position], position)
        }
    }

    override fun getItemCount(): Int = noteList.size

    fun addNote(note: Note) {
        noteList.add(note)
    }
}