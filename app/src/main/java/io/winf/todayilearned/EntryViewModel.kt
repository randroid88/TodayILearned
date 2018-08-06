package io.winf.todayilearned

import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View

class EntryViewModel : ViewModel() {

    fun onClickSaveEntry(view: View) {
        Log.d("EntryViewModel", "onClickSaveEntry");
    }
}
