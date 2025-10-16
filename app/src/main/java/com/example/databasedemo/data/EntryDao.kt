package com.example.databasedemo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<Entry>)

    @Query("SELECT * FROM entries ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomEntry(): Entry?
}
