package com.alexey.notes.notes_list.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.alexey.notes.R
import com.alexey.notes.about.AboutActivity
import com.alexey.notes.databinding.FragmentNotesListBinding
import com.alexey.notes.notes_list.Note
import com.alexey.notes.notes_list.NoteAdapter
import com.alexey.notes.notes_list.model.NotesListModel
import com.alexey.notes.notes_list.note.view.NoteFragment
import com.alexey.notes.notes_list.presenter.NotesListPresenter
import com.alexey.notes.notes_list.presenter.Presenter

class NotesListFragment : Fragment(), NotesListView {

    private lateinit var presenter: Presenter
    private lateinit var binding: FragmentNotesListBinding
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
        return binding.root
    }

    private fun init() {
        presenter = NotesListPresenter(NotesListModel())
        presenter.attachView(this)
        presenter.initList()

        adapter.setOnNoteClickListener(object : NoteAdapter.OnNoteClickListener{
            override fun onNoteClick(note: Note, position: Int) {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragment_container, NoteFragment.newInstance(note))
                    ?.addToBackStack(null)
                    ?.commit()
            }
        })
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
     * Открытие экрана "О приложении"
     */
    override fun openAboutScreen() {
        startActivity(Intent(activity, AboutActivity::class.java))
    }
}