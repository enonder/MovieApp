package com.elifnuronder.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.model.TimePeriod
import com.elifnuronder.movieapp.domain.use_case.GetAllFavoritesUseCase
import com.elifnuronder.movieapp.domain.use_case.GetFavoriteStatusUseCase
import com.elifnuronder.movieapp.domain.use_case.GetPopularMoviesByTimePeriodUseCase
import com.elifnuronder.movieapp.domain.use_case.ToggleFavoriteUseCase
import com.elifnuronder.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    val movies: List<Movie> = emptyList(),
    val selectedTimePeriod: TimePeriod = TimePeriod.TODAY,
    val favoriteMovieIds: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedMovie: Movie? = null,
    val showMovieDetails: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesByTimePeriodUseCase: GetPopularMoviesByTimePeriodUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getFavoriteStatusUseCase: GetFavoriteStatusUseCase,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase
) : ViewModel() {
    
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state
    
    init {
        loadMovies()
        observeFavorites()
    }
    
    private fun observeFavorites() {
        getAllFavoritesUseCase()
            .onEach { favoriteMovies ->
                val favoriteIds = favoriteMovies.map { it.id }.toSet()
                _state.value = _state.value.copy(favoriteMovieIds = favoriteIds)
            }
            .launchIn(viewModelScope)
    }
    
    fun loadMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            
            when (val result = getPopularMoviesByTimePeriodUseCase(_state.value.selectedTimePeriod)) {
                is Resource.Success -> {
                    val movies = result.data ?: emptyList()
                    _state.value = _state.value.copy(
                        movies = movies,
                        isLoading = false
                    )
                    // Favorite statuses are automatically updated via observeFavorites()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    // Already handling loading state
                }
            }
        }
    }
    
    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteUseCase(movie)
            // Favorite status will be automatically updated via observeFavorites()
        }
    }
    
    fun selectTimePeriod(timePeriod: TimePeriod) {
        _state.value = _state.value.copy(selectedTimePeriod = timePeriod)
        loadMovies()
    }
    
    fun retry() {
        loadMovies()
    }
    
    fun showMovieDetails(movie: Movie) {
        _state.value = _state.value.copy(
            selectedMovie = movie,
            showMovieDetails = true
        )
    }
    
    fun hideMovieDetails() {
        _state.value = _state.value.copy(
            selectedMovie = null,
            showMovieDetails = false
        )
    }
}
