package io.winf.todayilearned.utils

import io.winf.todayilearned.EmptyEntry
import io.winf.todayilearned.Entry

class EntryCreator(private val dateTimeFetcher: DateTimeFetcher = DateTimeFetcher()) {
    fun create(entryText: String): Entry {
        return when {
            entryIsValid(entryText) -> Entry(entryText, dateTimeFetcher.now())
            else -> EmptyEntry()
        }
    }

    private fun entryIsValid(entryText: String) = !entryText.isBlank() && !entryText.isEmpty()
}