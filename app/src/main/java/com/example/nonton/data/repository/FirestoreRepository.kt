package com.example.nonton.data.repository

import com.example.nonton.data.model.Movie
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val moviesCollection = db.collection("movies")

    suspend fun addMovie(movie: Movie) {
        moviesCollection.document(movie.id.toString()).set(movie).await()
    }

    suspend fun getMovies(): List<Movie> {
        return try {
            val snapshot = moviesCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(Movie::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateMovie(movie: Movie) {
        moviesCollection.document(movie.id.toString()).set(movie).await()
    }

    suspend fun deleteMovie(movieId: String) {
        moviesCollection.document(movieId).delete().await()
    }
}