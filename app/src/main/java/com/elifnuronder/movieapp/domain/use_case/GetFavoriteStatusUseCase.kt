package com.elifnuronder.movieapp.domain.use_case

import com.elifnuronder.movieapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteStatusUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(movieId: Int): Boolean {
        return favoriteRepository.isFavorite(movieId)
    }
}