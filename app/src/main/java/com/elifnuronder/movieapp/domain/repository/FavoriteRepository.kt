package com.elifnuronder.movieapp.domain.repository

import com.elifnuronder.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun addToFavorites(movie: Movie)
    suspend fun removeFromFavorites(movieId: Int)
    suspend fun isFavorite(movieId: Int): Boolean
    fun getAllFavorites(): Flow<List<Movie>>
    suspend fun getFavoriteCount(): Int
}