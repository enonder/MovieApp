package com.elifnuronder.movieapp.viewmodel

import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.model.TimePeriod
import com.elifnuronder.movieapp.domain.use_case.GetAllFavoritesUseCase
import com.elifnuronder.movieapp.domain.use_case.GetFavoriteStatusUseCase
import com.elifnuronder.movieapp.domain.use_case.GetPopularMoviesByTimePeriodUseCase
import com.elifnuronder.movieapp.domain.use_case.GetUpcomingMoviesUseCase
import com.elifnuronder.movieapp.domain.use_case.ToggleFavoriteUseCase
import com.elifnuronder.movieapp.util.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var getPopularMoviesByTimePeriodUseCase: GetPopularMoviesByTimePeriodUseCase
    private lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private lateinit var getFavoriteStatusUseCase: GetFavoriteStatusUseCase
    private lateinit var getAllFavoritesUseCase: GetAllFavoritesUseCase
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testMovies = listOf(
        Movie(
            id = 1, title = "Test Movie", overview = "Test", posterPath = "/test.jpg",
            backdropPath = null, releaseDate = "2023-01-01", voteAverage = 8.5,
            voteCount = 1000, adult = false, originalLanguage = "en",
            originalTitle = "Test Movie", popularity = 100.0, genreIds = listOf(1)
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        
        getPopularMoviesByTimePeriodUseCase = mockk()
        getUpcomingMoviesUseCase = mockk()
        toggleFavoriteUseCase = mockk()
        getFavoriteStatusUseCase = mockk()
        getAllFavoritesUseCase = mockk()

        // Default mocks
        coEvery { getPopularMoviesByTimePeriodUseCase(any(), any()) } returns Resource.Success(testMovies)
        coEvery { getUpcomingMoviesUseCase(any()) } returns Resource.Success(emptyList())
        every { getAllFavoritesUseCase() } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadMovies returns success`() = runTest {
        // Given
        viewModel = HomeViewModel(
            getPopularMoviesByTimePeriodUseCase, getUpcomingMoviesUseCase,
            toggleFavoriteUseCase, getFavoriteStatusUseCase, getAllFavoritesUseCase
        )

        // When
        viewModel.loadMovies()

        // Then
        val state = viewModel.state.value
        assertEquals(testMovies, state.movies)
        assertFalse(state.isTrendingLoading)
    }

    @Test
    fun `loadMovies handles error`() = runTest {
        // Given
        coEvery { getPopularMoviesByTimePeriodUseCase(any(), any()) } returns Resource.Error("Network error")
        
        viewModel = HomeViewModel(
            getPopularMoviesByTimePeriodUseCase, getUpcomingMoviesUseCase,
            toggleFavoriteUseCase, getFavoriteStatusUseCase, getAllFavoritesUseCase
        )

        // When
        viewModel.loadMovies()

        // Then
        val state = viewModel.state.value
        assertEquals("Network error", state.error)
        assertFalse(state.isTrendingLoading)
    }
}
