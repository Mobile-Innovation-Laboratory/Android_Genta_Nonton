package com.example.nonton.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nonton.data.model.Movie
import com.example.nonton.data.model.MovieResponse
import com.example.nonton.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movies = MutableStateFlow<MovieResponse?>(null)
    val movies: StateFlow<MovieResponse?> = _movies

    private val _movieDetail = MutableLiveData<Movie?>()
    val movieDetail: LiveData<Movie?> get() = _movieDetail

    fun fetchMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies(apiKey)
                _movies.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getMovieById(movieId: String, apiKey: String): Movie {
        return repository.getMovieById(movieId, apiKey)
    }

}

