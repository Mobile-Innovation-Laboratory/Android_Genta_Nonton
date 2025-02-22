package com.example.nonton.data.repository

import android.util.Log
import com.example.nonton.data.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NotesRepository(private val userId: String) {
    private val db = FirebaseFirestore.getInstance()
    private val notesCollection = db.collection("users").document(userId).collection("notes")

    suspend fun addNote(content: String) {
        val newNote = hashMapOf(
            "content" to content,
            "createdAt" to FieldValue.serverTimestamp()
        )
        notesCollection.add(newNote).await()
    }

    fun getNotes(): Flow<List<Note>> = callbackFlow {
        val listener = notesCollection.orderBy("createdAt")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                val notesList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Note::class.java)?.copy(id = doc.id)
                } ?: emptyList()

                trySend(notesList)
            }

        awaitClose { listener.remove() }
    }

    suspend fun updateNote(noteId: String, newContent: String) {
        notesCollection.document(noteId)
            .update("content", newContent, "updatedAt", FieldValue.serverTimestamp())
            .await()
    }

    suspend fun deleteNote(noteId: String) {
        notesCollection.document(noteId).delete().await()
    }
}

