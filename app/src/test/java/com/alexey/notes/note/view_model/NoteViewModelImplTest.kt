package com.alexey.notes.note.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexey.notes.note.repository.NotesRepository
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock


@RunWith(JUnit4::class)
class NoteViewModelImplTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel = NoteViewModelImpl()
    private var repository = mock(NotesRepository::class.java)

    @Before
    fun setUp() {
        viewModel.attachRepository(repository)
    }

    @Test
    fun tryToSaveEmptyNote() {
        var saveEmptyContent = false

        viewModel.apply {
            save()
            onAttemptSaveEmptyContent.observeForever {
                saveEmptyContent = true
            }
        }
        assertTrue(saveEmptyContent)
    }

    @Test
    fun saveNewNote() {
        var saveNewNote = false

        viewModel.apply {
            title = "title"
            text = "text"

            save()
            onSaveSuccessEvent.observeForever {
                saveNewNote = true
            }
        }
        assertTrue(saveNewNote)
    }

    @Test
    fun saveExistingNote() {
        var saveExistingNote = false

        viewModel.apply {
            title = "title"
            text = "text"
            id = 1L
            Mockito.`when`(repository.updateNote(id, title, text)).thenReturn(true)

            save()
            onSaveSuccessEvent.observeForever {
                saveExistingNote = true
            }
        }
        assertTrue(saveExistingNote)
    }

    @Test
    fun tryToShareEmptyNote() {
        var shareEmptyContent = false

        viewModel.apply {
            shareBtnClicked()
            onAttemptShareEmptyContent.observeForever {
                shareEmptyContent = true
            }
        }
        assertTrue(shareEmptyContent)
    }

    @Test
    fun shareNote() {
        var shareNote = false

        viewModel.apply {
            title = "title"
            text = "text"

            shareBtnClicked()
            onShareEvent.observeForever {
                shareNote = true
            }
        }
        assertTrue(shareNote)
    }
}