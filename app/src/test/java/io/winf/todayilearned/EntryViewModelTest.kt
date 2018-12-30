package io.winf.todayilearned

import android.app.Application
import androidx.lifecycle.LiveData
import io.winf.todayilearned.utils.TestArgGenerator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times

class EntryViewModelTest {

    @Mock
    var mockApplication: Application = mock(Application::class.java)

    @Mock
    var mockRepository: EntryRepository = mock(EntryRepository::class.java)

    @Mock
    var mockAllEntries: LiveData<*>? = mock(LiveData::class.java)

    private lateinit var entryViewModel: EntryViewModel
    private lateinit var entry: Entry
    private lateinit var actualAllEntries: LiveData<List<Entry>>

    @Before
    @Suppress("UNCHECKED_CAST")
    fun setup() {
        Mockito.`when`(mockRepository.allEntries).thenReturn(mockAllEntries as LiveData<List<Entry>>?)
    }

    @Test
    fun allEntries() {
        givenEntryViewModel()

        whenAllEntries()

        thenAllEntriesIsFromRepository()
    }

    @Test
    fun insert() {
        givenEntryViewModel()
        givenValidEntry()

        whenInsert()

        thenEntryIsInsertedIntoRepository()
    }

    private fun givenValidEntry() {
        entry = Entry(TestArgGenerator.anyString(), TestArgGenerator.anyLong())
    }

    private fun givenEntryViewModel() {
        entryViewModel = EntryViewModel(mockApplication, mockRepository)
    }

    private fun whenAllEntries() {
        actualAllEntries = entryViewModel.allEntries
    }

    private fun whenInsert() {
        entryViewModel.insert(entry)
    }

    private fun thenAllEntriesIsFromRepository() {
        assertEquals(mockAllEntries, actualAllEntries)
    }

    private fun thenEntryIsInsertedIntoRepository() {
        Mockito.verify(mockRepository, times(1)).insert(entry)
    }
}