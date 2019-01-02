package io.winf.todayilearned.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entry::class], version = 1)
abstract class EntryRoomDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao

    companion object {

        @Volatile
        private var INSTANCE: EntryRoomDatabase? = null

        fun getDatabase(context: Context): EntryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(EntryRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                EntryRoomDatabase::class.java, "entry_database")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE as EntryRoomDatabase
        }
    }
}