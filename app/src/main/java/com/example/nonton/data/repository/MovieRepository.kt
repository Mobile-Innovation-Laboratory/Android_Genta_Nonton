package com.example.nonton.data.repository

import com.example.nonton.data.model.Movie
import com.example.nonton.data.model.MovieResponse
import com.example.nonton.data.remote.MovieApi
import com.example.nonton.data.remote.RetrofitInstance
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getPopularMovies(apiKey: String): MovieResponse {
        return movieApi.getPopularMovies(apiKey)
    }

    suspend fun getMovieById(movieId: String, apiKey: String): Movie {
        return RetrofitInstance.api.getMovieDetails(movieId, apiKey)
    }
}