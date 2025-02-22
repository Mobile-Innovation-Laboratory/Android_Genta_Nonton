package com.example.nonton.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nonton.data.model.Note
import com.example.nonton.data.repository.NotesRepository
import com.example.nonton.ui.viewmodel.NotesViewModel
import com.example.nonton.ui.viewmodel.NotesViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navController: NavController? = null,
    repository: NotesRepository
) {
    val viewModel: NotesViewModel = viewModel(factory = NotesViewModelFactory(repository))
    val notes by viewModel.notes.collectAsState()
    var newNote by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes", style = MaterialTheme.typography.headlineSmall) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (newNote.isNotBlank()) {
                        viewModel.addNote(newNote)
                        newNote = ""
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = newNote,
                onValueChange = { newNote = it },
                placeholder = { Text("Write something...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))
            if (notes.isEmpty()) {
                Text(
                    "No notes available",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            } else {
                LazyColumn {
                    items(notes) { note ->
                        NoteItem(
                            note,
                            onDelete = { viewModel.deleteNote(note.id) },
                            onUpdate = { newContent -> viewModel.updateNote(note.id, newContent) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: () -> Unit, onUpdate: (String) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    var editedText by remember { mutableStateOf(note.content) }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            if (isEditing) {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onUpdate(editedText)
                        isEditing = false
                    }) {
                        Text("Update")
                    }
                    TextButton(onClick = { isEditing = false }) {
                        Text("Cancel")
                    }
                }
            } else {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(note.content, style = MaterialTheme.typography.bodyLarge)
                    Row {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Note")
                        }
                        IconButton(onClick = onDelete) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Note", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}
