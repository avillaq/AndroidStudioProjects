package com.example.habittracker.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Splash : Screen("splash")
    object Streaks : Screen("streaks")
    object History : Screen("history/{habitId}") {
        fun createRoute(habitId: Int) = "history/$habitId"
    }
}