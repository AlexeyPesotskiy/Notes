package com.alexey.notes.note

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexey.notes.Constants
import com.alexey.notes.R
import com.alexey.notes.databinding.ActivityMainBinding
import com.alexey.notes.note.view.NoteFragment
import com.alexey.notes.notes_list.recycler.Note

class NoteActivity : AppCompatActivity(), HomeButtonSupport {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val note = intent.getSerializableExtra(Constants.NOTE_ITEM) as Note
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, NoteFragment.newInstance(note))
            .commit()
    }

    override fun showHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun hideHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}