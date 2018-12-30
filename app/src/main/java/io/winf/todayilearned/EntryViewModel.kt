package io.winf.todayilearned

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class EntryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EntryRepository = EntryRepository(application)

    internal val allEntries: LiveData<List<Entry>>

    init {
        allEntries = repository.allEntries
    }

    internal fun insert(entry: Entry) {
        repository.insert(entry)
    }
}
