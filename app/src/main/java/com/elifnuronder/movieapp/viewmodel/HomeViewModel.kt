package com.elifnuronder.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.model.TimePeriod
import com.elifnuronder.movieapp.domain.use_case.GetPopularMoviesByTimePeriodUseCase
import com.elifnuronder.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    val movies: List<Movie> = emptyList(),
    val selectedTimePeriod: TimePeriod = TimePeriod.TODAY,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesByTimePeriodUseCase: GetPopularMoviesByTimePeriodUseCase
) : ViewModel() {
    
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state
    
    init {
        loadMovies()
    }
    
    fun loadMovies() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            
            when (val result = getPopularMoviesByTimePeriodUseCase(_state.value.selectedTimePeriod)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        movies = result.data ?: emptyList(),
                        isLoading = false
                    )
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
    
    fun selectTimePeriod(timePeriod: TimePeriod) {
        _state.value = _state.value.copy(selectedTimePeriod = timePeriod)
        loadMovies()
    }
    
    fun retry() {
        loadMovies()
    }
}
