package com.alexey.notes.note

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.alexey.notes.R
import com.alexey.notes.note.view.NoteView
import java.lang.IllegalStateException

class DialogSaveNoteFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.save)
                .setMessage(R.string.want_save_note)
                .setPositiveButton(R.string.ok) { _, _ ->
                    (parentFragment as NoteView).continueSave()
                }
                .setNegativeButton(R.string.cancel, null)
                .create()
        } ?: throw IllegalStateException("Exception")
}