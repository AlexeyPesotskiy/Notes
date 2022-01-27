package com.alexey.notes.note.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexey.notes.Constants
import com.alexey.notes.R
import com.alexey.notes.databinding.FragmentNoteBinding
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.note.DialogSaveNoteFragment
import com.alexey.notes.note.HomeButtonSupport
import com.alexey.notes.note.repository.NotesRepositoryImpl
import com.alexey.notes.note.view_model.NoteViewModel
import com.alexey.notes.note.view_model.NoteViewModelFactory
import com.alexey.notes.note.view_model.NoteViewModelImpl
import com.alexey.notes.notes_list.MainActivity

/**
 * Вью для [NoteViewModelImpl]
 */
class NoteFragment : Fragment(), NoteView {

    companion object {
        private lateinit var dB: AppDataBase

        fun newInstance(noteID: Long, dataBase: AppDataBase): NoteFragment {
            val bundle = Bundle()
            bundle.putLong(Constants.ARG_NOTE_ID, noteID)

            return NoteFragment().apply {
                arguments = bundle
                dB = dataBase
            }
        }

        const val ACTION = "com.alexey.notes.action_saving"
        const val NOTE_INFO = "note_info"
    }

    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(
            this, NoteViewModelFactory(NotesRepositoryImpl(dB))
        )[NoteViewModelImpl::class.java]

        subscribeToViewModel()

        setHasOptionsMenu(true)
        (activity as HomeButtonSupport).showHomeButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentNoteBinding>(
            inflater,
            R.layout.fragment_note,
            container,
            false
        ).also {
            arguments?.getLong(Constants.ARG_NOTE_ID).let {
                viewModel.init(it ?: 0L)
            }

            it.viewModel = this.viewModel
            binding = it
        }.root

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.note_menu, menu)
    }

    /**
     * Обработка нажатий элементов toolbar
     *
     * @param item элемент toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> viewModel.backBtnClicked()
            R.id.note_share -> viewModel.shareBtnClicked()
            R.id.note_save -> {
                DialogSaveNoteFragment().show(childFragmentManager, "save")
            }
            R.id.note_delete -> viewModel.deleteBtnClicked()
        }
        return true
    }

    /**
     * Сохраняем заметку после нажатия на кнопку "ОК" в диалоговом окне
     */
    override fun continueSave() {
        binding.apply {
            viewModel?.save()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.onSaveSuccessEvent.observe(this) {
            showToast(R.string.note_saved)
            activity?.sendBroadcast(Intent().apply {
                action = ACTION
                putExtra(NOTE_INFO, "id: ${it.id} title: ${it.title}")
            })
        }

        viewModel.onSaveFailedEvent.observe(this) {
            showToast(R.string.note_save_failed)
        }

        viewModel.onAttemptSaveEmptyContent.observe(this) {
            showToast(R.string.note_empty_save)
        }


        viewModel.onShareEvent.observe(this) {
            shareNote(it.title, it.text)
        }

        viewModel.onAttemptShareEmptyContent.observe(this) {
            showToast(R.string.note_empty_share)
        }


        viewModel.onDeleteSuccessEvent.observe(this) {
            showToast(R.string.note_deleted)
        }

        viewModel.onDeleteFailedEvent.observe(this) {
            showToast(R.string.note_delete_failed)
        }


        viewModel.onBackEvent.observe(this) {
            if (activity is MainActivity)
                activity?.supportFragmentManager?.popBackStack()
            else
                activity?.finish()

            (activity as HomeButtonSupport).hideHomeButton()
        }
    }

    /**
     * Показать всплывающее сообщение
     *
     * @param resId идентификатор строкового ресурса сообщения
     */
    private fun showToast(resId: Int) {
        Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()
    }

    /**
     * Отправить заметку в стороннее приложение
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun shareNote(title: String, text: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "$title\n$text")
        })
    }
}