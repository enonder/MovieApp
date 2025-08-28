package com.elifnuronder.movieapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elifnuronder.movieapp.domain.model.Movie

@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    favoriteMovieIds: Set<Int> = emptySet(),
    modifier: Modifier = Modifier,
    onMovieClick: (Movie) -> Unit = {},
    onFavoriteClick: (Movie) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
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
