package com.example.proyectofinal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyectofinal.ui.screens.AdaptativoScreen
import com.example.proyectofinal.ui.screens.CirculoScreen
import com.example.proyectofinal.ui.screens.HomeScreen
import com.example.proyectofinal.ui.screens.ResultScreen
import com.example.proyectofinal.ui.screens.FormularioScreen
import com.example.proyectofinal.ui.screens.PreferenciasScreen
import com.example.proyectofinal.ui.screens.RecetasScreen
import com.example.proyectofinal.ui.screens.TimerScreen
import com.example.proyectofinal.ui.screens.WorkerScreen
import com.example.proyectofinal.ui.screens.crud.AccionesAdminScreen
import com.example.proyectofinal.ui.screens.crud.CrearRecetasScreen
import com.example.proyectofinal.ui.screens.crud.ListaRecetasScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreens.Timer.route) {
        composable(
            route = AppScreens.Home.route // ruta "/home"
        ) {
            // ruta que lleva a HomeScreen
            HomeScreen(onNavigate = { mensaje: String ->
                navController.navigate("result/$mensaje")
            })
        }
        composable(
            route = AppScreens.Adaptativo.route // ruta "/adaptativo"
        ) {
            AdaptativoScreen()
        }
        composable(
            route = AppScreens.Formulario.route // ruta "/registro"
        ) {
            FormularioScreen(onNavigate = { mensaje: String ->
                navController.navigate("result/$mensaje")
            })
        }
        composable(
            route = AppScreens.Circulo.route // ruta "/circulo"
        ) {
            CirculoScreen()
        }
        composable(
            route = AppScreens.Recetas.route // ruta "/recetas"
        ) {
            RecetasScreen()
        }
        composable (
            route = AppScreens.Preferencias.route // ruta "/preferencias"
        ) {
            PreferenciasScreen()
        }
        composable(
            route = AppScreens.Result.route, // ruta "result/{mensaje}"
            arguments = listOf(navArgument("mensaje") { type = NavType.StringType })
        ) { backStackEntry ->
            // capturamos el mensaje del parametro
            val mensaje = backStackEntry.arguments?.getString("mensaje") ?: ""
            // ruta que lleva a ResultScreen
            ResultScreen(
                mensaje = mensaje,
                onNavigate = {
                    navController.popBackStack()
                }
            )
        }

        /* CRUD */
        // acciones de administrador
        composable (
            route = AppScreens.AccionesAdmin.route // ruta "/accionesAdmin"
        ) {
            AccionesAdminScreen(
                onNavigateToCrear = {
                    navController.navigate(AppScreens.CrearRecetas.route)
                },
                onNavigateToLista = {
                    navController.navigate(AppScreens.ListaRecetas.route)
                }
            )
        }
        // crear recetas en formulario
        composable (
            route = AppScreens.CrearRecetas.route // ruta "/crearRecetas"
        ) {
            CrearRecetasScreen(
                onNavigateToBack = {
                    navController.popBackStack()
                }
            )
        }
        // lista de todas las recetas
        composable (
            route = AppScreens.ListaRecetas.route // ruta "/listaRecetas"
        ) {
            ListaRecetasScreen(
                onNavigateToBack = {
                    navController.popBackStack()
                }
            )
        }

        // Prueba simple worker en segundo plano
        composable (
            route = AppScreens.Worker.route // ruta "/worker"
        ) {
            WorkerScreen(
                context = navController.context
            )
        }

        // Notificacion con timer
        composable (
            route = AppScreens.Timer.route // ruta "/timer"
        ) {
            TimerScreen()
        }
    }
}
