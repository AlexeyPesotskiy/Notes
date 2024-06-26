package com.alexey.notes.notes_list.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.alexey.notes.Constants
import com.alexey.notes.R
import com.alexey.notes.about.AboutActivity
import com.alexey.notes.background_tasks.BackupWorker
import com.alexey.notes.databinding.FragmentNotesListBinding
import com.alexey.notes.db.AppDataBase
import com.alexey.notes.note.NotePagerActivity
import com.alexey.notes.note.view.NoteFragment
import com.alexey.notes.notes_list.recycler.NoteAdapter
import com.alexey.notes.notes_list.repository.NotesRepositoryImpl
import com.alexey.notes.notes_list.view_model.NotesListViewModel
import com.alexey.notes.notes_list.view_model.NotesListViewModelFactory
import com.alexey.notes.notes_list.view_model.NotesListViewModelImpl
import java.util.concurrent.TimeUnit

/**
 * Вью для [NotesListViewModelImpl]
 */
class NotesListFragment : Fragment(), NotesListView {

    companion object {
        private lateinit var dB: AppDataBase

        fun newInstance(dataBase: AppDataBase): NotesListFragment =
            NotesListFragment().apply {
                dB = dataBase
            }
    }

    private lateinit var binding: FragmentNotesListBinding
    private lateinit var viewModel: NotesListViewModel
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
        viewModel = ViewModelProvider(
            this, NotesListViewModelFactory(NotesRepositoryImpl(dB))
        )[NotesListViewModelImpl::class.java]

        viewModel.updateList()
        subscribeToViewModel()

        setupWorker()

        adapter.setOnNoteClickListener { id ->
            startActivity(Intent(activity, NotePagerActivity::class.java)
                .putExtra(Constants.NOTE_POSITION, id))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_notes_menu, menu)
        setupSearch(menu)
    }

    /**
     * Обработка нажатий элементов toolbar
     *
     * @param item элемент toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_app -> viewModel.aboutBtnClicked()
            R.id.note_download -> viewModel.downloadBtnClicked()
        }
        return true
    }

    private fun setupSearch(menu: Menu) {
        (menu.findItem(R.id.search)?.actionView as SearchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean = false

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.updateListOnSearch(newText)
                    return false
                }
            })
    }

    private fun setupWorker() {
        WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork(
                BackupWorker.TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequest
                    .Builder(BackupWorker::class.java, 15, TimeUnit.MINUTES)
                    .build()
            )
    }

    private fun subscribeToViewModel() {
        viewModel.onUpdateNotesEvent.observe(this) {
            adapter.submitList(it)
        }

        viewModel.onDownloadSuccessEvent.observe(this) { _: Unit? ->
            Toast.makeText(activity, R.string.note_downloaded, Toast.LENGTH_SHORT).show()
        }

        viewModel.onDownloadFailedEvent.observe(this) { _: Unit? ->
            Toast.makeText(activity, R.string.note_download_failed, Toast.LENGTH_SHORT).show()
        }

        viewModel.onAboutBtnClickedEvent.observe(this) { _: Unit? ->
            openAboutScreen()
        }

        viewModel.onAddNoteBtnClicked.observe(this) { _: Unit? ->
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