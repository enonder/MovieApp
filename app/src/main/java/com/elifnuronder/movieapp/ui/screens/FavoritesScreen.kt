package com.elifnuronder.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elifnuronder.movieapp.ui.components.FavoriteMovieItem
import com.elifnuronder.movieapp.ui.components.MovieDetailsBottomSheet
import com.elifnuronder.movieapp.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()
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
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Text(
                text = "My Favorites",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            
            if (favoriteMovies.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No favorite movies yet",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Add movies to favorites by tapping the heart icon",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            } else {
                // Favorites list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = if (state.isSelectionMode) 80.dp else 0.dp),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favoriteMovies) { movie ->
                        FavoriteMovieItem(
                            movie = movie,
                            isSelected = viewModel.isMovieSelected(movie.id),
                            onSelectionChange = { _ ->
                                viewModel.toggleSelection(movie.id)
                            },
                            onClick = {
                                viewModel.showMovieDetails(movie)
                            }
                        )
                    }
                }
            }
        }
        
        // Bottom action bar for selection mode
        if (state.isSelectionMode) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                shadowElevation = 8.dp,
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${state.selectedMovieIds.size} selected",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(
                            onClick = { viewModel.clearSelection() }
                        ) {
                            Text("Cancel")
                        }
                        
                        Button(
                            onClick = { viewModel.removeSelectedFromFavorites() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Remove Selected")
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
}