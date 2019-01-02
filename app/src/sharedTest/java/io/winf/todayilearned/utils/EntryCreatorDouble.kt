package io.winf.todayilearned.utils

import io.winf.todayilearned.data.EmptyEntry
import io.winf.todayilearned.data.Entry

class EntryCreatorDouble : EntryCreator() {
    private var entry: Entry = EmptyEntry()

    override fun create(entryText: String): Entry {
        entry = super.create(entryText)
        return entry
    }

    fun getEntry(): Entry {
        return entry
    }
}