package com.alexey.notes.notes_list.note.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alexey.notes.R
import com.alexey.notes.databinding.FragmentNoteBinding
import com.alexey.notes.notes_list.MainActivity
import com.alexey.notes.notes_list.Note
import com.alexey.notes.notes_list.note.model.NoteModel
import com.alexey.notes.notes_list.note.presenter.NotePresenter
import com.alexey.notes.notes_list.note.presenter.Presenter

class NoteFragment : Fragment(), NoteView {

    companion object {
        private const val ARG_NOTE = "args_note"
        fun newInstance(note: Note) : NoteFragment {
            val fragment = NoteFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARG_NOTE, note)
            fragment.arguments = bundle
            return fragment
        }

    }

    private lateinit var binding: FragmentNoteBinding
    private lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        presenter = NotePresenter(NoteModel())
        presenter.attachView(this)

        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentNoteBinding.inflate(inflater).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (arguments?.getSerializable(ARG_NOTE) as Note).apply {
            presenter.init(mTitle, mText)
        }
    }

    override fun fillLayout(title: String, text: String) {
        binding.editTitle.setText(title)
        binding.editText.setText(text)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
    }

    /**
     * Обработка нажатий элементов toolbar
     *
     * @param item элемент toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val title = binding.editTitle.text.toString()
        val text = binding.editText.text.toString()
        when (item.itemId) {
            android.R.id.home -> presenter.backBtnClicked()
            R.id.note_share -> presenter.shareBtnClicked(title, text)
            R.id.note_save -> presenter.save(title, text)
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    /**
     * Удалось сохранить заметку
     */
    override fun onSaveSuccessEvent() {
        showToast(R.string.note_saved)
    }

    /**
     * Не удалось сохранить заметку
     */
    override fun onSaveFailedEvent() {
        showToast(R.string.note_save_failed)
    }

    /**
     * Попытка сохранить пустую заметку
     */
    override fun onAttemptSaveEmptyContent() {
        showToast(R.string.note_empty_save)
    }

    /**
     * Попытка поделиться пустой заметкой
     */
    override fun onAttemptShareEmptyContent() {
        showToast(R.string.note_empty_share)
    }

    /**
     * Нажатие на кнопку назад
     */
    override fun onBackEvent() {
        activity?.supportFragmentManager?.popBackStack()
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