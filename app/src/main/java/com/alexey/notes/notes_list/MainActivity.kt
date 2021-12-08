package com.alexey.notes.notes_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexey.notes.R
import com.alexey.notes.databinding.ActivityMainBinding
import com.alexey.notes.notes_list.note.presenter.NotePresenter
import com.alexey.notes.notes_list.view.NotesListFragment

/**
 * Вью для [NotePresenter]
 */
class MainActivity : AppCompatActivity(), HomeButtonSupport{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, NotesListFragment())
                .commit()
        }
    }

    override fun showHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun hideHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}