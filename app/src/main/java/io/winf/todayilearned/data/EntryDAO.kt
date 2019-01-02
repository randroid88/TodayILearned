package io.winf.todayilearned.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface EntryDao {

    @get:Query("SELECT * from entry_table ORDER BY entryDateTime")
    val orderedEntries: LiveData<List<Entry>>

    @Insert
    fun insert(entry: Entry)

    @Query("DELETE FROM entry_table")
    fun deleteAll()
}