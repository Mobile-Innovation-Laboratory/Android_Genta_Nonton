package com.example.nonton.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nonton.data.model.Note
import com.example.nonton.data.repository.NotesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            repository.getNotes()
                .collect { noteList ->
                    _notes.value = noteList
                }
        }
    }

    // Create: Tambah catatan ke Firestore
    fun addNote(content: String) {
        viewModelScope.launch {
            repository.addNote(content)
        }
    }

    // Update: Perbarui catatan berdasarkan ID
    fun updateNote(noteId: String, newContent: String) {
        viewModelScope.launch {
            repository.updateNote(noteId, newContent)
        }
    }

    // Delete: Hapus catatan berdasarkan ID
    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }
}



