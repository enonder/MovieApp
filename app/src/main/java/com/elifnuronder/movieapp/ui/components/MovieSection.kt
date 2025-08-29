package com.elifnuronder.movieapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elifnuronder.movieapp.domain.model.Movie
import com.elifnuronder.movieapp.domain.model.TimePeriod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    favoriteMovieIds: Set<Int> = emptySet(),
    modifier: Modifier = Modifier,
    selectedTimePeriod: TimePeriod? = null,
    onTimePeriodSelected: ((TimePeriod) -> Unit)? = null,
    onMovieClick: (Movie) -> Unit = {},
    onFavoriteClick: (Movie) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Title and optional time period filter on same line
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // Time period filter chips (if provided)
            if (selectedTimePeriod != null && onTimePeriodSelected != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TimePeriod.values().forEach { timePeriod ->
                        FilterChip(
                            onClick = { onTimePeriodSelected(timePeriod) },
                            label = { 
                                Text(
                                    text = timePeriod.displayName,
                                    style = MaterialTheme.typography.labelMedium
                                ) 
                            },
                            selected = selectedTimePeriod == timePeriod,
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }
        }
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies) { movie ->
                MovieCard(
                    movie = movie,
                    isFavorite = favoriteMovieIds.contains(movie.id),
                    onClick = { onMovieClick(movie) },
                    onFavoriteClick = { onFavoriteClick(movie) }
                )
            }
        }
    }
}
