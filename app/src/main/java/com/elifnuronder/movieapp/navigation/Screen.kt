package com.elifnuronder.movieapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Outlined.Home)
    data object Favorites : Screen("favorites", "Favorites", Icons.Outlined.FavoriteBorder)
    data object Settings : Screen("settings", "Settings", Icons.Outlined.Settings)
}
