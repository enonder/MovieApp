package com.elifnuronder.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elifnuronder.movieapp.ui.components.AppHeader
import com.elifnuronder.movieapp.ui.components.MovieDetailsBottomSheet
import com.elifnuronder.movieapp.ui.components.MovieSection
import com.elifnuronder.movieapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    
    // Show/hide bottom sheet based on state
    LaunchedEffect(state.showMovieDetails) {
        if (state.showMovieDetails) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        AppHeader()
        
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isTrendingLoading && state.movies.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            
                state.error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (state.error?.contains("internet", ignoreCase = true) == true) {
                            Text(
                                text = "📶",
                                style = MaterialTheme.typography.displayMedium,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "No Internet Connection",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "Please check your internet connection and try again",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            Text(
                                text = state.error ?: "Something went wrong",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Button(
                            onClick = { viewModel.retry() },
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Text("Try Again")
                        }
                    }
                }
                
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        if (state.movies.isNotEmpty()) {
                            item {
                                MovieSection(
                                    title = "Trending Movies",
                                    movies = state.movies,
                                    favoriteMovieIds = state.favoriteMovieIds,
                                    selectedTimePeriod = state.selectedTimePeriod,
                                    onTimePeriodSelected = { timePeriod ->
                                        viewModel.selectTimePeriod(timePeriod)
                                    },
                                    onMovieClick = { movie ->
                                        viewModel.showMovieDetails(movie)
                                    },
                                    onFavoriteClick = { movie ->
                                        viewModel.toggleFavorite(movie)
                                    }
                                )
                            }
                        }
                        
                        if (state.upcomingMovies.isNotEmpty()) {
                            item {
                                MovieSection(
                                    title = "Upcoming Movies",
                                    movies = state.upcomingMovies,
                                    favoriteMovieIds = state.favoriteMovieIds,
                                    onMovieClick = { movie ->
                                        viewModel.showMovieDetails(movie)
                                    },
                                    onFavoriteClick = { movie ->
                                        viewModel.toggleFavorite(movie)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Movie Details Bottom Sheet
    if (state.showMovieDetails && state.selectedMovie != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.hideMovieDetails() },
            sheetState = bottomSheetState
        ) {
            MovieDetailsBottomSheet(
                movie = state.selectedMovie!!,
                onDismiss = { viewModel.hideMovieDetails() }
            )
        }
    }
}