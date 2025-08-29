package com.elifnuronder.movieapp.data.remote

import com.elifnuronder.movieapp.data.remote.dto.MovieDto
import com.elifnuronder.movieapp.data.remote.dto.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MovieListResponse>
    
    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MovieListResponse>
    
    @GET("discover/movie")
    suspend fun getUpcomingMoviesFiltered(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("release_date.gte") releaseDateFrom: String,
        @Query("release_date.lte") releaseDateTo: String,
        @Query("sort_by") sortBy: String = "release_date.asc",
        @Query("region") region: String = "US",
        @Query("with_release_type") releaseType: String = "3|2"
    ): Response<MovieListResponse>
    
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
