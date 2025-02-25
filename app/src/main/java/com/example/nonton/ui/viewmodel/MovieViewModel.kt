package com.example.nonton.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nonton.data.model.Movie
import com.example.nonton.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _movieDetail = MutableLiveData<Movie?>()
    val movieDetail: LiveData<Movie?> get() = _movieDetail

    fun fetchMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies(apiKey)
                _movies.value = response.movies
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getMovieById(movieId: String, apiKey: String): Movie {
        return repository.getMovieById(movieId, apiKey)
    }

}

