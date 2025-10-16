package com.example.databasedemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databasedemo.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository) : ViewModel() {

    private val _text = MutableStateFlow("Press the button to show a random sentence.")
    val text = _text.asStateFlow()

    fun loadRandom() {
        viewModelScope.launch {
            val entry = repo.getRandomEntry()
            _text.value = entry?.text ?: "No entries found in database."
        }
    }
}
