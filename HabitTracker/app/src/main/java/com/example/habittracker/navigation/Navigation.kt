package com.example.habittracker.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.habittracker.data.HabitViewModel
import com.example.habittracker.ui.screens.HistoryScreen
import com.example.habittracker.ui.screens.HomeScreen
import com.example.habittracker.ui.screens.SplashScreen
import com.example.habittracker.ui.screens.StreakScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.Home.route) {
            val viewModel: HabitViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onNavigateToStreaks = { navController.navigate(Screen.Streaks.route) },
                onNavigateToHistory = { habitId ->
                    navController.navigate(Screen.History.createRoute(habitId))
                }
            )
        }
        composable(route = Screen.Streaks.route) {
            val viewModel: HabitViewModel = hiltViewModel()
            StreakScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.History.route,
            arguments = listOf(navArgument("habitId") { type = NavType.IntType })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt("habitId") ?: 0
            val viewModel: HabitViewModel = hiltViewModel()
            HistoryScreen(
                habitId = habitId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}