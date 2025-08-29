package com.elifnuronder.movieapp.domain.repository

import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.model.TimePeriod
import com.elifnuronder.movieapp.util.Resource

interface MovieRepository {
    suspend fun getPopularMovies(page: Int = 1): Resource<List<Movie>>
    suspend fun getPopularMoviesByTimePeriod(timePeriod: TimePeriod, page: Int = 1): Resource<List<Movie>>
    suspend fun getUpcomingMovies(page: Int = 1): Resource<List<Movie>>
    // Removed: getTopRatedMovies, getNowPlayingMovies - not used in current app
}
