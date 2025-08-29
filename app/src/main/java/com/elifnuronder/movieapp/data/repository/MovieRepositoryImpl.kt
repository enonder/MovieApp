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
    

    
    override suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>> {
        return try {
            // Get current date and 3 months from now for proper upcoming filtering
            val currentDate = java.time.LocalDate.now()
            val sixMonthsFromNow = currentDate.plusMonths(6)
            val dateFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
            
            val fromDate = currentDate.format(dateFormatter)
            val toDate = sixMonthsFromNow.format(dateFormatter)
            

            
            val response = api.getUpcomingMoviesFiltered(
                apiKey = apiKey,
                page = page,
                releaseDateFrom = fromDate,
                releaseDateTo = toDate
            )
            
            if (response.isSuccessful) {
                response.body()?.let { movieListResponse ->
                    // Additional client-side filtering to ensure no past dates
                    val today = java.time.LocalDate.now()
                    val filteredMovies = movieListResponse.results
                        .map { it.toMovie() }
                        .filter { movie ->
                            try {
                                val movieDate = java.time.LocalDate.parse(movie.releaseDate)
                                movieDate.isAfter(today) || movieDate.isEqual(today)
                            } catch (e: Exception) {
                                // If date parsing fails, include the movie
                                true
                            }
                        }
                    

                    Resource.Success(filteredMovies)
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Failed to fetch upcoming movies: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
}

