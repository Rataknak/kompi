package com.example.kompi_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kompi_app.screen.HomeScreen
import com.example.kompi_app.screen.DiscoverScreen
import com.example.kompi_app.FavoritesScreen
import com.example.kompi_app.screen.ProfileScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Discover : Screen("discover")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Discover.route) {
            DiscoverScreen()
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}
