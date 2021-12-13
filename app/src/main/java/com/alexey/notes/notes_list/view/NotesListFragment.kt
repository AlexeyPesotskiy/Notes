package com.alexey.notes.notes_list.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.alexey.notes.Constants
import com.alexey.notes.R
import com.alexey.notes.about.AboutActivity
import com.alexey.notes.databinding.FragmentNotesListBinding
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.note.NotePagerActivity
import com.alexey.notes.note.view.NoteFragment
import com.alexey.notes.notes_list.recycler.Note
import com.alexey.notes.notes_list.recycler.NoteAdapter
import com.alexey.notes.notes_list.model.NotesListModel
import com.alexey.notes.notes_list.presenter.NotesListPresenter
import com.alexey.notes.notes_list.presenter.Presenter

class NotesListFragment : Fragment(), NotesListView {

    companion object {
        fun newInstance(dataBase: AppDataBase) : NotesListFragment {
            val fragment = NotesListFragment()
            fragment.dB = dataBase
            return fragment
        }
    }

    private lateinit var binding: FragmentNotesListBinding
    private lateinit var presenter: Presenter
    private lateinit var dB: AppDataBase
    private var adapter = NoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNotesListBinding.inflate(inflater)
        binding.listNotes.adapter = adapter

        binding.btnAdd.setOnClickListener {
            presenter.addNoteBtnClicked()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter.updateList()
    }

    private fun init() {
        presenter = NotesListPresenter(NotesListModel(dB))
        presenter.attachView(this)
        presenter.initList()

        adapter.setOnNoteClickListener { position ->
            startActivity(Intent(activity, NotePagerActivity::class.java)
                .putExtra(Constants.NOTE_POSITION, position))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_notes_menu, menu)
    }

    /**
     * Обработка нажатий элементов toolbar
     *
     * @param item элемент toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_app -> presenter.aboutBtnClicked()
        }
        return true
    }

    /**
     * Добавление заметки в RecyclerView
     */
    override fun addNote(note: Note) {
        adapter.addNote(note)
    }

    /**
     * Добавление заметки в RecyclerView
     */
    override fun updateNote(note: Note) {
        adapter.updateNote(note)
    }

    /**
     * Открытие экрана "О приложении"
     */
    override fun openAboutScreen() {
        startActivity(Intent(activity, AboutActivity::class.java))
    }

    /**
     * Открытие экрана "Новая заметка"
     */
    override fun openNewNote() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, NoteFragment.newInstance(0L, dB))
            ?.addToBackStack(null)
            ?.commit()
    }
}