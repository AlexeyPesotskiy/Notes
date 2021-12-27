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
import com.alexey.notes.note.view_model.NoteViewModelImpl
import com.alexey.notes.notes_list.MainActivity

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
    }

    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[NoteViewModelImpl::class.java]
        viewModel.attachRepository(NotesRepositoryImpl(dB))

        subscribeToViewModel()

        (activity as HomeButtonSupport).showHomeButton()
        setHasOptionsMenu(true)
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
            it.viewModel = this.viewModel
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLong(Constants.ARG_NOTE_ID).apply {
            viewModel.init(this ?: 0L)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as HomeButtonSupport).hideHomeButton()
    }

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


        viewModel.onBackEvent.observe(this) {
            if (activity is MainActivity)
                activity?.supportFragmentManager?.popBackStack()
            else
                activity?.finish()
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