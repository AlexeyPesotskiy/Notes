package com.alexey.notes.note.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexey.notes.R
import com.alexey.notes.databinding.ActivityNoteBinding
import com.alexey.notes.note.model.NoteModel
import com.alexey.notes.note.presenter.NotePresenter
import com.alexey.notes.note.presenter.Presenter

class NoteActivity : AppCompatActivity(), NoteView {

    private lateinit var binding: ActivityNoteBinding

    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val mainModel = NoteModel()
        presenter = NotePresenter(mainModel)
        presenter.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.note_save) with(binding) {
            presenter.save(editTitle.text.toString(), editText.text.toString())
        }
        return true
    }

    override fun onSaveSuccessEvent() {
        showToast(R.string.note_saved)
    }

    override fun onSaveFailedEvent() {
        showToast(R.string.note_save_failed)
    }

    override fun onAttemptSaveEmptyContent() {
        showToast(R.string.note_empty)
    }

    private fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}