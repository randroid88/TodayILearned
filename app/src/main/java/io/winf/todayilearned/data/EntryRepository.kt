package io.winf.todayilearned.data

import android.app.Application
import androidx.lifecycle.LiveData
import org.jetbrains.anko.doAsync

class EntryRepository internal constructor(application: Application) {

    private val entryDao: EntryDao
    val allEntries: LiveData<List<Entry>>

    init {
        val db = EntryRoomDatabase.getDatabase(application)
        entryDao = db.entryDao()
        allEntries = entryDao.orderedEntries
    }

    fun getEntry(entryId: Int): LiveData<Entry> {
        return entryDao.getEntry(entryId)
    }

    fun insert(entry: Entry) {
        doAsync {
            entryDao.insert(entry)
        }
    }

    fun updateEntry(entryId: Int, entryText: String) {
        doAsync {
            entryDao.updateEntry(entryId, entryText)
        }
    }
}