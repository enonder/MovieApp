package com.elifnuronder.movieapp.data.repository

import com.elifnuronder.movieapp.data.local.dao.FavoriteMovieDao
import com.elifnuronder.movieapp.data.local.entity.toFavoriteEntity
import com.elifnuronder.movieapp.data.local.entity.toMovie
import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteMovieDao
) : FavoriteRepository {
    
    override suspend fun addToFavorites(movie: Movie) {
        favoriteDao.insertFavorite(movie.toFavoriteEntity())
    }
    
    override suspend fun removeFromFavorites(movieId: Int) {
        favoriteDao.deleteFavoriteById(movieId)
    }
    
    override suspend fun isFavorite(movieId: Int): Boolean {
        return favoriteDao.isFavorite(movieId)
    }
    
    override fun getAllFavorites(): Flow<List<Movie>> {
        return favoriteDao.getAllFavorites().map { entities ->
            entities.map { it.toMovie() }
        }
    }
    
    override suspend fun getFavoriteCount(): Int {
        return favoriteDao.getFavoriteCount()
    }
}