package com.example.nonton.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val movies: List<Movie>
)

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    val releaseDate: String? = null

) {
    fun getFullPosterUrl() = "https://image.tmdb.org/t/p/w500/$posterPath"
}