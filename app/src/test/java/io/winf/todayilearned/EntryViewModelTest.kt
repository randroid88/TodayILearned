package io.winf.todayilearned

import android.app.Application
import androidx.lifecycle.LiveData
import io.winf.todayilearned.data.Entry
import io.winf.todayilearned.data.EntryRepository
import io.winf.todayilearned.utils.EntryCreatorDouble
import io.winf.todayilearned.utils.TestArgGenerator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*

class EntryViewModelTest {

    @Mock
    var mockApplication: Application = mock(Application::class.java)

    @Mock
    var mockRepository: EntryRepository = mock(EntryRepository::class.java)

    @Mock
    var mockAllEntries: LiveData<*>? = mock(LiveData::class.java)

    private var entryCreatorDouble: EntryCreatorDouble = EntryCreatorDouble()
    private var entryId: Int = -1

    private lateinit var entryViewModel: EntryViewModel
    private lateinit var entryText: String
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
    fun updateOrInsert_Insert() {
        givenEntryViewModel()
        givenValidEntryText()
        givenEntryDoesNotExist()

        whenUpdateOrInsert()

        thenEntryIsInsertedIntoRepository()
    }

    @Test
    fun updateOrInsert_Insert_EmptyEntryText() {
        givenEntryViewModel()
        givenEmptyEntryText()
        givenEntryDoesNotExist()

        whenUpdateOrInsert()

        thenEntryIsNotInsertedIntoRepository()
    }

    @Test
    fun updateOrInsert_Update() {
        givenEntryViewModel()
        givenValidEntryText()
        givenEntryExists()

        whenUpdateOrInsert()

        thenEntryIsUpdatedInRepository()
    }

    private fun givenEntryExists() {
        entryId = 1
    }

    private fun givenValidEntryText() {
        entryText = TestArgGenerator.anyString()
    }

    private fun givenEntryDoesNotExist() {
        entryId = 0
    }

    private fun givenEntryViewModel() {
        entryViewModel = EntryViewModel(mockApplication, mockRepository)
        entryViewModel.entryCreator = entryCreatorDouble
    }

    private fun givenEmptyEntryText() {
        entryText = ""
    }

    private fun whenAllEntries() {
        actualAllEntries = entryViewModel.allEntries
    }

    private fun whenUpdateOrInsert() {
        entryViewModel.updateOrInsert(entryId, entryText)
    }

    private fun thenAllEntriesIsFromRepository() {
        assertEquals(mockAllEntries, actualAllEntries)
    }

    private fun thenEntryIsInsertedIntoRepository() {
        Mockito.verify(mockRepository, times(1)).insert(entryCreatorDouble.getEntry())
    }

    private fun thenEntryIsNotInsertedIntoRepository() {
        Mockito.verify(mockRepository, times(0)).insert(entryCreatorDouble.getEntry())
    }

    private fun thenEntryIsUpdatedInRepository() {
        verify(mockRepository, times(1)).updateEntry(entryId, entryText)
    }
}