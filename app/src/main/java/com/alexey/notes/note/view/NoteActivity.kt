package com.alexey.notes.note.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexey.notes.R
import com.alexey.notes.about.AboutActivity
import com.alexey.notes.databinding.ActivityNoteBinding
import com.alexey.notes.note.model.NoteModel
import com.alexey.notes.note.presenter.NotePresenter
import com.alexey.notes.note.presenter.Presenter

/**
 * Вью для [NotePresenter]
 */
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
        presenter = NotePresenter(NoteModel())
        presenter.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
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
            R.id.note_share -> presenter.shareBtnClicked(title, text)
            R.id.note_save -> presenter.save(title, text)
            R.id.about_app -> presenter.aboutBtnClicked()
        }
        return true
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
     * Показать всплывающее сообщение
     *
     * @param resId идентификатор строкового ресурса сообщения
     */
    private fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
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

    /**
     * Открытие экрана "О приложении"
     */
    override fun openAboutScreen() {
        startActivity(Intent(this, AboutActivity::class.java))
    }
}