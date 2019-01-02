package io.winf.todayilearned

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.winf.todayilearned.data.Entry
import io.winf.todayilearned.data.EntryDao
import io.winf.todayilearned.data.EntryRoomDatabase
import io.winf.todayilearned.util.OneTimeObserver
import io.winf.todayilearned.utils.TestArgGenerator
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class EntryDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var entryDao: EntryDao
    private lateinit var db: EntryRoomDatabase

    private lateinit var expectedEntries: List<Entry>
    private lateinit var expectedEntry: Entry
    private lateinit var actualEntries: List<Entry>
    private lateinit var actualEntry: Entry

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EntryRoomDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        entryDao = db.entryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun noEntries() {
        thenNoEntriesExist()
    }

    @Test
    @Throws(Exception::class)
    fun insertEntry() {
        givenEntries(1)

        whenEntriesInserted()

        thenEntriesExist()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEntriesInOrder() {
        givenEntries(3)

        whenEntriesInserted()

        thenEntriesExist()
        thenEntriesSizeIs(3)
        thenEntriesIdsIncrement()
        thenEntriesTextIsAsSet()
        thenEntriesDateTimeIsAsSet()
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllEntries() {
        givenEntriesInserted()

        whenAllEntriesDeleted()

        thenNoEntriesExist()
    }

    @Test
    fun getEntry() {
        givenEntriesInserted()
        val entryId = givenEntryExists()

        whenGetEntryWithId(entryId)

        thenEntryIdIs(entryId)
        thenEntryTextIsAsSet()
        thenEntryDateTimeIsAsSet()
    }

    @Test(expected = KotlinNullPointerException::class)
    fun getEntry_EntryDoesNotExist() {
        givenEntriesInserted()
        val entryId = givenEntryDoesNotExist()

        whenGetEntryWithId(entryId)

        // KotlinNullPointerException in OneTimeObserver.kt because db returns null
    }

    @Test
    fun updateEntry() {
        givenEntriesInserted()
        val entryId = givenEntryExists()
        val newEntryText: String = givenNewEntryText(entryId)

        whenUpdateEntry(entryId, newEntryText)

        thenEntryWasUpdated(entryId)
    }

    // Helper Kotlin extension for testing LiveData
    private fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }

    private fun givenEntries(numberOfEntries: Int) {
        var firstEntryDateTime = TestArgGenerator.anyLong() - numberOfEntries
        expectedEntries = List(numberOfEntries) {
            Entry(TestArgGenerator.anyString(), firstEntryDateTime++)
        }
    }

    private fun givenEntriesInserted() {
        givenEntries(NUMBER_OF_ENTRIES)

        whenEntriesInserted()

        thenEntriesExist()
    }

    private fun givenEntryExists(): Int {
        val entryId = Random.nextInt(1, NUMBER_OF_ENTRIES)
        expectedEntry = expectedEntries[entryId - 1]
        return entryId
    }

    private fun givenEntryDoesNotExist() = NUMBER_OF_ENTRIES + 1

    private fun givenNewEntryText(entryId: Int): String {
        val existingEntry = expectedEntries[entryId - 1]
        val existingEntryText = existingEntry.entryText
        var entryText = existingEntryText

        while (existingEntryText.equals(entryText)) {
            entryText = TestArgGenerator.anyString()
        }

        expectedEntry = Entry(entryText, existingEntry.entryDateTime)

        return entryText
    }

    private fun whenEntriesInserted() {
        for (entry in this.expectedEntries)
            entryDao.insert(entry)
    }

    private fun whenAllEntriesDeleted() {
        entryDao.deleteAll()
    }

    private fun whenGetEntryWithId(entryId: Int) {
        entryDao.getEntry(entryId).observeOnce {
            actualEntry = it
        }
    }

    private fun whenUpdateEntry(entryId: Int, newEntryText: String) {
        entryDao.updateEntry(entryId, newEntryText)
    }

    private fun thenNoEntriesExist() {
        entryDao.orderedEntries.observeOnce {
            assertEquals(0, it.size)
        }
    }

    private fun thenEntriesExist() {
        entryDao.orderedEntries.observeOnce {
            assertNotEquals(0, it.size)
            actualEntries = it
        }
    }

    private fun thenEntriesSizeIs(expectedSize: Int) {
        assertEquals(expectedSize, actualEntries.size)
    }

    private fun thenEntriesTextIsAsSet() {
        for (i in 0 until expectedEntries.size) {
            assertEquals(expectedEntries[i].entryText, actualEntries[i].entryText)
        }
    }

    private fun thenEntriesDateTimeIsAsSet() {
        for (i in 0 until expectedEntries.size) {
            assertEquals(expectedEntries[i].entryDateTime, actualEntries[i].entryDateTime)
        }
    }

    private fun thenEntriesIdsIncrement() {
        for (i in 0 until expectedEntries.size) {
            assertEquals(i + 1, actualEntries[i].id)
        }
    }

    private fun thenEntryIdIs(entryId: Int) {
        assertEquals(entryId, actualEntry.id)
    }

    private fun thenEntryTextIsAsSet() {
        assertEquals(expectedEntry.entryText, actualEntry.entryText)
    }

    private fun thenEntryDateTimeIsAsSet() {
        assertEquals(expectedEntry.entryDateTime, actualEntry.entryDateTime)
    }

    private fun thenEntryWasUpdated(entryId: Int) {
        whenGetEntryWithId(entryId)
        thenEntryIdIs(entryId)
        thenEntryTextIsAsSet()
        thenEntryDateTimeIsAsSet()
    }
}

const val NUMBER_OF_ENTRIES = 3


