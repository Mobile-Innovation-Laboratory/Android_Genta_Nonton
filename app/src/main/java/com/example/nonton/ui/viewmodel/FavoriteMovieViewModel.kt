package com.example.nonton.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.nonton.data.local.AppDatabase
import com.example.nonton.data.local.entity.FavoriteMovie
import com.example.nonton.data.repository.FavoriteMovieRepository
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteMovieRepository
    val favoriteMovies: LiveData<List<FavoriteMovie>>

    init {
        val dao = AppDatabase.getDatabase(application).favoriteMovieDao()
        repository = FavoriteMovieRepository(dao)
        favoriteMovies = repository.getAllFavorites().asLiveData()
    }

    fun addFavorite(movie: FavoriteMovie) = viewModelScope.launch {
        repository.addFavorite(movie)
    }

    fun removeFavorite(movie: FavoriteMovie) = viewModelScope.launch {
        repository.removeFavorite(movie)
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return repository.getFavoriteMovie(movieId) != null
    }
}

