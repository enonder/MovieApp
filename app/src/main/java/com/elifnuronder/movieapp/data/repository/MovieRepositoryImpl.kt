package com.elifnuronder.movieapp.data.repository

import com.elifnuronder.movieapp.data.remote.TmdbApi
import com.elifnuronder.movieapp.data.remote.dto.toMovie
import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.model.TimePeriod
import com.elifnuronder.movieapp.domain.repository.MovieRepository
import com.elifnuronder.movieapp.util.Constants
import com.elifnuronder.movieapp.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {
    
    private val apiKey = Constants.TMDB_API_KEY
    
    override suspend fun getPopularMovies(page: Int): Resource<List<Movie>> {
        return try {
            val response = api.getPopularMovies(apiKey, page)
            if (response.isSuccessful) {
                response.body()?.let { movieListResponse ->
                    Resource.Success(movieListResponse.results.map { it.toMovie() })
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed to fetch popular movies: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
    
    override suspend fun getPopularMoviesByTimePeriod(timePeriod: TimePeriod, page: Int): Resource<List<Movie>> {
        return try {
            val timeWindow = when (timePeriod) {
                TimePeriod.TODAY -> "day"
                TimePeriod.THIS_WEEK -> "week"
            }
            
            val response = api.getTrendingMovies(timeWindow, apiKey, page)
            if (response.isSuccessful) {
                response.body()?.let { movieListResponse ->
                    Resource.Success(movieListResponse.results.map { it.toMovie() })
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed to fetch ${timePeriod.displayName.lowercase()} trending movies: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
    
    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return try {
            val response = api.getTopRatedMovies(apiKey, page)
            if (response.isSuccessful) {
                response.body()?.let { movieListResponse ->
                    Resource.Success(movieListResponse.results.map { it.toMovie() })
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed to fetch top rated movies: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
    
    override suspend fun getNowPlayingMovies(page: Int): Resource<List<Movie>> {
        return try {
            val response = api.getNowPlayingMovies(apiKey, page)
            if (response.isSuccessful) {
                response.body()?.let { movieListResponse ->
                    Resource.Success(movieListResponse.results.map { it.toMovie() })
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed to fetch now playing movies: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
    
    override suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>> {
        return try {
            val response = api.getUpcomingMovies(apiKey, page)
            if (response.isSuccessful) {
                response.body()?.let { movieListResponse ->
                    Resource.Success(movieListResponse.results.map { it.toMovie() })
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed to fetch upcoming movies: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
}

