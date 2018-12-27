package io.winf.todayilearned

import android.app.Application
import android.os.AsyncTask
import android.arch.lifecycle.LiveData

class EntryRepository internal constructor(application: Application) {

    private val entryDao: EntryDao
    val allEntries: LiveData<List<Entry>>

    init {
        val db = EntryRoomDatabase.getDatabase(application)
        entryDao = db.entryDao()
        allEntries = entryDao.orderedEntries
    }
}