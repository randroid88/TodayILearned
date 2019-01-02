package io.winf.todayilearned

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.winf.todayilearned.data.EntryRepository

@Suppress("UNCHECKED_CAST")
class EntryViewModelFactory(private val mApplication: Application, private val repository: EntryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EntryViewModel(mApplication, repository) as T
    }
}