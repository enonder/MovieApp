package com.elifnuronder.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elifnuronder.movieapp.ui.screens.FavoritesScreen
import com.elifnuronder.movieapp.ui.screens.HomeScreen
import com.elifnuronder.movieapp.ui.screens.SettingsScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
