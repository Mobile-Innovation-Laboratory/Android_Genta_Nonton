package com.example.nonton.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen/{movieId}") {
        fun createRoute(movieId: String) = "detail_screen/$movieId"
    }
    object Favorite : Screen("favorite_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Profile : Screen("profile_screen")
    object Note : Screen("note")
}