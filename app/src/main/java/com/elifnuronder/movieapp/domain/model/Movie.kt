package com.elifnuronder.movieapp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val adult: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val genreIds: List<Int>
) {
    fun getPosterUrl(): String? {
        return posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
    }
    
    fun getBackdropUrl(): String? {
        return backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }
    }
}
