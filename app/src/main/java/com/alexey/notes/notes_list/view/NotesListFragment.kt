package com.alexey.notes.notes_list.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexey.notes.Constants
import com.alexey.notes.R
import com.alexey.notes.about.AboutActivity
import com.alexey.notes.databinding.FragmentNotesListBinding
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.note.NotePagerActivity
import com.alexey.notes.note.view.NoteFragment
import com.alexey.notes.notes_list.repository.NotesRepositoryImpl
import com.alexey.notes.notes_list.view_model.NotesListViewModelImpl
import com.alexey.notes.notes_list.recycler.NoteAdapter

class NotesListFragment : Fragment(), NotesListView {

    companion object {
        private lateinit var dB: AppDataBase

        fun newInstance(dataBase: AppDataBase): NotesListFragment =
            NotesListFragment().apply {
                dB = dataBase
            }
    }

    private lateinit var binding: FragmentNotesListBinding
    private lateinit var viewModel: NotesListViewModelImpl
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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notes_list,
            container,
            false
        )

        return binding.apply {
            listNotes.adapter = adapter
            btnAdd.setOnClickListener {
                viewModel.addNoteBtnClicked()
            }
        }.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateList()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[NotesListViewModelImpl::class.java]

        subscribeToViewModel()

        viewModel.apply {
            attachRepository(NotesRepositoryImpl(dB))
            initList()
        }

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
            R.id.about_app -> viewModel.aboutBtnClicked()
        }
        return true
    }

    private fun subscribeToViewModel() {
        viewModel.onAddNoteEvent.observe(this) {
            for (note in it)
                adapter.addNote(note)
        }

        viewModel.onUpdateNoteEvent.observe(this) {
            adapter.updateNote(it)
        }

        viewModel.onAboutBtnClickedEvent.observe(this) {
            openAboutScreen()
        }

        viewModel.onAddNoteBtnClicked.observe(this) {
            openNewNote()
        }
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