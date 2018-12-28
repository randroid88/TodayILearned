package io.winf.todayilearned

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

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
                                .addCallback(roomDatabaseCallback)
                                .build()
                    }
                }
            }
            return INSTANCE as EntryRoomDatabase
        }

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // ToDo: Remove temporary dummy data
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }
    }


}

private class PopulateDbAsync internal constructor(db: EntryRoomDatabase) : AsyncTask<Void, Void, Void>() {

    private val dao: EntryDao

    init {
        dao = db.entryDao()
    }

    override fun doInBackground(vararg params: Void): Void? {
        dao.deleteAll()
        var entry = Entry("Today I learned how to store text entries in a Room database.", System.currentTimeMillis())
        dao.insert(entry)
        entry = Entry("Today I learned why the Kotlin Annotation Processor is needed.", System.currentTimeMillis())
        dao.insert(entry)
        return null
    }
}