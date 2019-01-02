package io.winf.todayilearned.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
open class Entry(val entryText: String,
                 val entryDateTime: Long = 0L) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

class EmptyEntry : Entry("", 0L)