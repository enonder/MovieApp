package com.elifnuronder.movieapp.util

import com.elifnuronder.movieapp.BuildConfig

object Constants {
    // TMDB API key from local.properties
    const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
    
    // Image URLs
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    const val POSTER_SIZE_W500 = "w500"
    const val BACKDROP_SIZE_W780 = "w780"
}
