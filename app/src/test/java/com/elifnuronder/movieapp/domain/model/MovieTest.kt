package com.elifnuronder.movieapp.domain.model

import org.junit.Assert.*
import org.junit.Test

class MovieTest {

    @Test
    fun `getPosterUrl returns correct URL when posterPath is not null`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test overview",
            posterPath = "/test_poster.jpg",
            backdropPath = null,
            releaseDate = "2023-01-01",
            voteAverage = 8.5,
            voteCount = 1000,
            adult = false,
            originalLanguage = "en",
            originalTitle = "Test Movie",
            popularity = 100.0,
            genreIds = listOf(1, 2, 3)
        )

        // When
        val posterUrl = movie.getPosterUrl()

        // Then
        assertEquals("https://image.tmdb.org/t/p/w500/test_poster.jpg", posterUrl)
    }

    @Test
    fun `getPosterUrl returns null when posterPath is null`() {
        // Given
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test overview",
            posterPath = null,
            backdropPath = null,
            releaseDate = "2023-01-01",
            voteAverage = 8.5,
            voteCount = 1000,
            adult = false,
            originalLanguage = "en",
            originalTitle = "Test Movie",
            popularity = 100.0,
            genreIds = listOf(1, 2, 3)
        )

        // When
        val posterUrl = movie.getPosterUrl()

        // Then
        assertNull(posterUrl)
    }
}
