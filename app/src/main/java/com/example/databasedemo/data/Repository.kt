package com.example.databasedemo.data

class Repository(private val dao: EntryDao) {
    suspend fun getRandomEntry(): Entry? = dao.getRandomEntry()
}
