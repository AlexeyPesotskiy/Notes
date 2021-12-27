package com.alexey.notes.notes_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexey.notes.R
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.note.HomeButtonSupport
import com.alexey.notes.note.view_model.NoteViewModelImpl
import com.alexey.notes.notes_list.view.NotesListFragment

/**
 * Вью для [NoteViewModelImpl]
 */
class MainActivity : AppCompatActivity(), HomeButtonSupport {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataBase = AppDataBase.getDataBase(this)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, NotesListFragment.newInstance(dataBase))
                .commit()
    }

    override fun showHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun hideHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}