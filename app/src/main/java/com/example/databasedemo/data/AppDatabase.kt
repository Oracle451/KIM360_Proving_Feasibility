package com.example.databasedemo.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Entry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "entries.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Prepopulate data asynchronously
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).entryDao().insertAll(prepopulateEntries())
                            }
                            // Log the prepopulation
                            Log.d("AppDatabase", "Prepopulating with ${prepopulateEntries().size} entries.")
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
