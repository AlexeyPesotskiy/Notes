package com.alexey.notes.notes_list.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexey.notes.R
import com.alexey.notes.databinding.NoteItemBinding

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteHolder>(NoteItemDiffCallback()) {

    private lateinit var onClickListener: (Long) -> Unit

    fun setOnNoteClickListener(listener: (Long) -> Unit) {
        onClickListener = listener
    }

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = NoteItemBinding.bind(item)

        fun bind(note: Note) = with(binding) {
            noteTitle.text = note.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder =
        NoteHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        )

    override fun onBindViewHolder(holder: NoteHolder, position: Int) = with(holder) {
        bind(getItem(position))

        itemView.setOnClickListener {
            onClickListener(getItem(absoluteAdapterPosition).id)
        }
    }

    class NoteItemDiffCallback : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem.title == newItem.title && oldItem.text == newItem.text
    }
}