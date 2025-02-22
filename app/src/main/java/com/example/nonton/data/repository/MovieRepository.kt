package com.example.nonton.data.repository

import com.example.nonton.data.model.MovieResponse
import com.example.nonton.data.remote.RetrofitInstance

class MovieRepository {
    suspend fun getPopularMovies(apiKey: String): MovieResponse {
        return RetrofitInstance.api.getPopularMovies(apiKey)
    }
}