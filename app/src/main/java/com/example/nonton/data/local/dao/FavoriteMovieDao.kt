package com.example.nonton.data.local.dao

import androidx.room.*
import com.example.nonton.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    suspend fun getFavoriteMovie(id: Int): FavoriteMovie?

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavorites(): Flow<List<FavoriteMovie>>
}
