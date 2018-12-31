package io.winf.todayilearned

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class EntryViewModel(application: Application, private val repository: EntryRepository = EntryRepository(application)) : AndroidViewModel(application) {

    internal val allEntries: LiveData<List<Entry>> = repository.allEntries

    internal fun insert(entry: Entry) {
        when (entry) {
            !is EmptyEntry -> repository.insert(entry)
        }
    }
}
