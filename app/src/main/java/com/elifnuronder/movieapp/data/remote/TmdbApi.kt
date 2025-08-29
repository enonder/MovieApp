package com.elifnuronder.movieapp.data.remote

import com.elifnuronder.movieapp.data.remote.dto.MovieDto
import com.elifnuronder.movieapp.data.remote.dto.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    
    // Used by: GetPopularMoviesByTimePeriodUseCase (for "All Time" period)
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MovieListResponse>
    
    // Used by: GetPopularMoviesByTimePeriodUseCase (for "Today" and "This Week")
    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String, // "day" or "week"
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MovieListResponse>
    
    // Used by: GetUpcomingMoviesUseCase (better filtered version)
    @GET("discover/movie")
    suspend fun getUpcomingMoviesFiltered(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("release_date.gte") releaseDateFrom: String,
        @Query("release_date.lte") releaseDateTo: String,
        @Query("sort_by") sortBy: String = "release_date.asc", // Closest dates first
        @Query("region") region: String = "US",
        @Query("with_release_type") releaseType: String = "3|2" // Theatrical releases only
    ): Response<MovieListResponse>
    
    // Used by: MovieDetailsBottomSheet (when clicking on movies)
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDto>
    
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    }
}
