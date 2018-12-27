package io.winf.todayilearned

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.AndroidViewModel



class EntryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EntryRepository = EntryRepository(application)

    internal val allEntries: LiveData<List<Entry>>

    init {
        allEntries = repository.allEntries
    }
}
