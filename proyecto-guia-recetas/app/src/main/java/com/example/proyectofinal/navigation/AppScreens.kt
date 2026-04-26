package com.example.proyectofinal.navigation

sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Result : AppScreens("result/{mensaje}")
    object Adaptativo : AppScreens("adaptativo")
    object Formulario : AppScreens("registro")
    object Circulo : AppScreens("circulo")
    object Recetas : AppScreens("recetas")
    object Preferencias : AppScreens("preferencias")
    object AccionesAdmin : AppScreens("accionesAdmin")
    object CrearRecetas : AppScreens("crearRecetas")
    object ListaRecetas : AppScreens("listaRecetas")
    object Worker : AppScreens("worker")
    object Timer : AppScreens("timer")

}