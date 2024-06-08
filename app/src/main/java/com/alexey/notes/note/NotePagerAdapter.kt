package com.alexey.notes.note

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.note.view.NoteFragment
import com.alexey.notes.notes_list.recycler.Note

class NotePagerAdapter(pagerActivity: NotePagerActivity)
    : FragmentStateAdapter(pagerActivity) {

    lateinit var items: ArrayList<Note>
    lateinit var dataBase: AppDataBase

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        NoteFragment.newInstance(items[position].id, dataBase)
}