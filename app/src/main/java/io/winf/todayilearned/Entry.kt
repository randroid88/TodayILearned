package io.winf.todayilearned

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "entry_table")
class Entry(val entryText: String,
            val entryDateTime: Long = 0L) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}