package com.alexey.notes.note.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alexey.notes.Constants
import com.alexey.notes.R
import com.alexey.notes.databinding.FragmentNoteBinding
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.note.HomeButtonSupport
import com.alexey.notes.note.model.NoteModel
import com.alexey.notes.note.presenter.NotePresenter
import com.alexey.notes.note.presenter.Presenter
import com.alexey.notes.notes_list.MainActivity

class NoteFragment : Fragment(), NoteView {

    companion object {
        fun newInstance(noteID: Long, dataBase: AppDataBase) : NoteFragment {
            val bundle = Bundle()
            bundle.putLong(Constants.ARG_NOTE_ID, noteID)

            val fragment = NoteFragment()
            fragment.arguments = bundle
            fragment.dB = dataBase

            return fragment
        }
    }

    private lateinit var binding: FragmentNoteBinding
    private lateinit var presenter: Presenter
    private lateinit var dB: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        presenter = NotePresenter(NoteModel(dB))
        presenter.attachView(this)

        (activity as HomeButtonSupport).showHomeButton()
        setHasOptionsMenu(true)
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

        arguments?.getLong(Constants.ARG_NOTE_ID).apply {
            presenter.init(this ?: 0L)
        }
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
        val title = binding.editTitle.text.toString()
        val text = binding.editText.text.toString()
        when (item.itemId) {
            android.R.id.home -> presenter.backBtnClicked()
            R.id.note_share -> presenter.shareBtnClicked(title, text)
            R.id.note_save -> {
                val dialog = AlertDialog.Builder(activity)
                dialog
                    .setTitle(R.string.save)
                    .setMessage(R.string.want_save_note)
                    .setPositiveButton(R.string.ok ) { _: DialogInterface, _: Int ->
                        presenter.save(title, text)
                    }
                    .setNegativeButton(R.string.cancel, null)
                    .create()
                    .show()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as HomeButtonSupport).hideHomeButton()
    }

    /**
     * Заполняем разметку экрана
     *
     * @param title заголовок заметки
     * @param text текст заметки
     */
    override fun fillLayout(title: String, text: String) {
        binding.editTitle.setText(title)
        binding.editText.setText(text)
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
        if (activity is MainActivity)
            activity?.supportFragmentManager?.popBackStack()
        else
            activity?.finish()
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