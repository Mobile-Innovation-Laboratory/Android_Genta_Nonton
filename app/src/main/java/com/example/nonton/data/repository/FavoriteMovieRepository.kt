package com.example.nonton.data.repository

import com.example.nonton.data.local.dao.FavoriteMovieDao
import com.example.nonton.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow

class FavoriteMovieRepository(private val dao: FavoriteMovieDao) {
    suspend fun addFavorite(movie: FavoriteMovie) = dao.insertFavorite(movie)
    suspend fun removeFavorite(movie: FavoriteMovie) = dao.deleteFavorite(movie)
    suspend fun getFavoriteMovie(id: Int) = dao.getFavoriteMovie(id)
    fun getAllFavorites(): Flow<List<FavoriteMovie>> = dao.getAllFavorites()
}

