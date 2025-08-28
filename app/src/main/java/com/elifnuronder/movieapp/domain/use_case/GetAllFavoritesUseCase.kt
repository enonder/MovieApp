package com.elifnuronder.movieapp.domain.use_case

import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return favoriteRepository.getAllFavorites()
    }
}
