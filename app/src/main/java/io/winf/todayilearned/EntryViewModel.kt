package io.winf.todayilearned

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.winf.todayilearned.data.EmptyEntry
import io.winf.todayilearned.data.Entry
import io.winf.todayilearned.data.EntryRepository
import io.winf.todayilearned.utils.EntryCreator


class EntryViewModel(application: Application, private val repository: EntryRepository) : AndroidViewModel(application) {

    @VisibleForTesting
    var entryCreator = EntryCreator()

    internal val allEntries: LiveData<List<Entry>> = repository.allEntries

    internal fun getEntry(entryId: Int): LiveData<Entry> {
        return repository.getEntry(entryId)
    }

    internal fun updateOrInsert(entryId: Int, entryText: String) {
        if (entryId == 0) {
            insert(entryText)
        } else {
            updateEntry(entryId, entryText)
        }
    }

    private fun insert(entryText: String) {
        val entry = entryCreator.create(entryText)

        when (entry) {
            !is EmptyEntry -> repository.insert(entry)
        }
    }

    private fun updateEntry(entryId: Int, entryText: String) {
        repository.updateEntry(entryId, entryText)
    }
}
