package com.example.habittracker.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
}