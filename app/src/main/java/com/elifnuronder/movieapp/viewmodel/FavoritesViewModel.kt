package com.elifnuronder.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.use_case.GetAllFavoritesUseCase
import com.elifnuronder.movieapp.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoritesScreenState(
    val selectedMovieIds: Set<Int> = emptySet(),
    val isSelectionMode: Boolean = false
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    
    private val _state = mutableStateOf(FavoritesScreenState())
    val state: State<FavoritesScreenState> = _state
    
    val favoriteMovies: StateFlow<List<Movie>> = getAllFavoritesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun toggleSelection(movieId: Int) {
        val currentSelection = _state.value.selectedMovieIds.toMutableSet()
        
        if (currentSelection.contains(movieId)) {
            currentSelection.remove(movieId)
        } else {
            currentSelection.add(movieId)
        }
        
        _state.value = _state.value.copy(
            selectedMovieIds = currentSelection,
            isSelectionMode = currentSelection.isNotEmpty()
        )
    }
    
    fun clearSelection() {
        _state.value = _state.value.copy(
            selectedMovieIds = emptySet(),
            isSelectionMode = false
        )
    }
    
    fun removeSelectedFromFavorites() {
        viewModelScope.launch {
            val selectedIds = _state.value.selectedMovieIds
            selectedIds.forEach { movieId ->
                favoriteRepository.removeFromFavorites(movieId)
            }
            clearSelection()
        }
    }
    
    fun isMovieSelected(movieId: Int): Boolean {
        return _state.value.selectedMovieIds.contains(movieId)
    }
}
