package com.elifnuronder.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elifnuronder.movieapp.ui.components.MovieSection
import com.elifnuronder.movieapp.ui.components.TimePeriodFilter
import com.elifnuronder.movieapp.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state
    
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            state.error != null -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error ?: "Unknown error occurred",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { viewModel.retry() }
                    ) {
                        Text("Retry")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Setup Instructions:",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "1. Get your API key from https://www.themoviedb.org/settings/api\n" +
                                        "2. Replace 'YOUR_TMDB_API_KEY_HERE' in MovieRepositoryImpl.kt\n" +
                                        "3. Rebuild and run the app",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        TimePeriodFilter(
                            selectedTimePeriod = state.selectedTimePeriod,
                            onTimePeriodSelected = { timePeriod ->
                                viewModel.selectTimePeriod(timePeriod)
                            }
                        )
                    }
                    
                    if (state.movies.isNotEmpty()) {
                        item {
                            MovieSection(
                                title = "${state.selectedTimePeriod.displayName}'s Trending Movies",
                                movies = state.movies,
                                onMovieClick = { movie ->
                                    // TODO: Navigate to movie detail screen
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
