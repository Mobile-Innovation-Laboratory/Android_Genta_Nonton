package com.example.nonton.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nonton.data.local.MovieDatabase
import com.example.nonton.data.local.entity.FavoriteMovie
import com.example.nonton.data.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteMovieRepository

    init {
        val movieDao = MovieDatabase.getDatabase(application).favoriteMovieDao()
        repository = FavoriteMovieRepository(movieDao)
    }

    fun isFavorite(movieId: Int): Flow<Boolean> = repository.isFavorite(movieId)
    fun getAllFavorites(): Flow<List<FavoriteMovie>> = repository.getAllFavorites()
    fun getMovieById(movieId: Int): Flow<FavoriteMovie?> = repository.getMovieById(movieId)

    fun addToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.addFavorite(movie)
        }
    }

    fun removeFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.removeFavorite(movie)
        }
    }
}

