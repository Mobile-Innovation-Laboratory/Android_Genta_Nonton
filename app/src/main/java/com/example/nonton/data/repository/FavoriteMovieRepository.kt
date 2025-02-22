package com.example.nonton.data.repository

import com.example.nonton.data.local.dao.FavoriteMovieDao
import com.example.nonton.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteMovieRepository(private val dao: FavoriteMovieDao) {
    suspend fun addFavorite(movie: FavoriteMovie) = dao.insert(movie)
    suspend fun removeFavorite(movie: FavoriteMovie) = dao.delete(movie)

    fun getAllFavorites(): Flow<List<FavoriteMovie>> = dao.getAllFavorites()

    fun getMovieById(movieId: Int): Flow<FavoriteMovie?> = dao.getMovieById(movieId)

    fun isFavorite(movieId: Int): Flow<Boolean> = dao.getMovieById(movieId).map { it != null }
}


