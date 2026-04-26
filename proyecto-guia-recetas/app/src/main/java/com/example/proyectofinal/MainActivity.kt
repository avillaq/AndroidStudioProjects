package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.navigation.AppNavigation
import com.example.proyectofinal.store.ThemeRepository
import com.example.proyectofinal.store.ThemeType
import com.example.proyectofinal.store.dataStore
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppEntry()
        }
    }
}

@Composable
fun AppEntry() {
    val context = LocalContext.current

    val themeRepository = remember {
        ThemeRepository(context.dataStore)
    }

    val themeType by themeRepository.themeTypeActual.collectAsState(initial = ThemeType.LIGHT)
    val useDarkTheme = when (themeType) {
        ThemeType.LIGHT -> false
        ThemeType.DARK -> true
    }

    ProyectoFinalTheme(darkTheme = useDarkTheme) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}