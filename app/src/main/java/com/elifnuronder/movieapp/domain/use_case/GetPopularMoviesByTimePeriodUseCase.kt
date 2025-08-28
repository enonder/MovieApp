package com.elifnuronder.movieapp.domain.use_case

import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.model.TimePeriod
import com.elifnuronder.movieapp.domain.repository.MovieRepository
import com.elifnuronder.movieapp.util.Resource
import javax.inject.Inject

class GetPopularMoviesByTimePeriodUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(timePeriod: TimePeriod, page: Int = 1): Resource<List<Movie>> {
        return repository.getPopularMoviesByTimePeriod(timePeriod, page)
    }
}
