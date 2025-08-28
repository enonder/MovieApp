package com.elifnuronder.movieapp.domain.use_case

import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movie: Movie): Boolean {
        val isFavorite = favoriteRepository.isFavorite(movie.id)
        return if (isFavorite) {
            favoriteRepository.removeFromFavorites(movie.id)
            false
        } else {
            favoriteRepository.addToFavorites(movie)
            true
        }
    }
}