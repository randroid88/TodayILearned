package io.winf.todayilearned

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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

@RunWith(AndroidJUnit4::class)
class EntryDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var entryDao: EntryDao
    private lateinit var db: EntryRoomDatabase

    private lateinit var expectedEntries: List<Entry>
    private lateinit var actualEntries: List<Entry>

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

    private fun givenEntriesInserted() {
        givenEntries(3)

        whenEntriesInserted()

        thenEntriesExist()
    }

    // Helper Kotlin extension for testing LiveData
    fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }

    private fun givenEntries(numberOfEntries: Int) {
        var firstEntryDateTime = TestArgGenerator.anyLong() - numberOfEntries
        expectedEntries = List(numberOfEntries) {
            Entry(TestArgGenerator.anyString(), firstEntryDateTime++)
        }
    }

    private fun whenEntriesInserted() {
        for (entry in this.expectedEntries)
            entryDao.insert(entry)
    }

    private fun whenAllEntriesDeleted() {
        entryDao.deleteAll()
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
}



