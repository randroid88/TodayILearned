package io.winf.todayilearned

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface EntryDao {

    @get:Query("SELECT * from entry_table ORDER BY entryDateTime")
    val orderedEntries: LiveData<List<Entry>>

    @Insert
    fun insert(entry: Entry)

    @Query("DELETE FROM entry_table")
    fun deleteAll()
}