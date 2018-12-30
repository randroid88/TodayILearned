package io.winf.todayilearned

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

    fun insert(entry: Entry) {
        doAsync {
            entryDao.insert(entry)
        }
    }
}