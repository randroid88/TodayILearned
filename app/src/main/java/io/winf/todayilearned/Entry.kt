package io.winf.todayilearned

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
class Entry(val entryText: String,
            val entryDateTime: Long = 0L) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}