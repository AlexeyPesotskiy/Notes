package com.alexey.notes.notes_list.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexey.notes.R
import com.alexey.notes.databinding.NoteItemBinding

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private lateinit var onClickListener: (Long) -> Unit

    fun setOnNoteClickListener(listener: (Long) -> Unit) {
        onClickListener = listener
    }

    private val noteList = ArrayList<Note>(0)

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = NoteItemBinding.bind(item)

        fun bind(note: Note) = with(binding) {
            noteTitle.text = note.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(noteList[position])

        holder.itemView.setOnClickListener {
            onClickListener(noteList[position].id)
        }
    }

    override fun getItemCount(): Int = noteList.size

    fun addNote(note: Note) {
        noteList.add(note)
        notifyItemInserted(noteList.size - 1)
    }

    fun updateNote(note: Note) {
        noteList.indexOfFirst {
            it.id == note.id
        }.let {
            if (it > -1) {
                noteList[it] = note
                notifyItemChanged(it)
            } else
                addNote(note)
        }
    }

    fun deleteNote(id: Long) {
        noteList.indexOfFirst {
            it.id == id
        }.let {
            noteList.removeAt(it)
            notifyItemRemoved(it)
            notifyItemRangeChanged(it, itemCount)
        }
    }

    fun deleteAllNotes() {
        noteList.clear()
        notifyDataSetChanged()
    }
}