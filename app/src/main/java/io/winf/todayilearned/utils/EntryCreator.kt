package io.winf.todayilearned.utils

import io.winf.todayilearned.Entry

class EntryCreator(private val dateTimeFetcher: DateTimeFetcher = DateTimeFetcher()) {
    fun create(entryText: String): Entry {
        return Entry(entryText, dateTimeFetcher.now())
    }
}