package com.example.proyectofinal.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.store.ThemeRepository
import com.example.proyectofinal.store.ThemeType
import com.example.proyectofinal.store.dataStore
import com.example.proyectofinal.ui.preferencias.PreferenciasViewModel
import com.example.proyectofinal.ui.preferencias.PreferenciasViewModelFactory
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenciasScreen() {
    val context = LocalContext.current // contexto actual

    // instancia ViewModel
    val viewModel: PreferenciasViewModel = viewModel (
        factory = PreferenciasViewModelFactory(ThemeRepository(context.dataStore))
    )

    val themeType by viewModel.themeType.collectAsState()
    val esModoOscuro = themeType == ThemeType.DARK

    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text("Preferencias de Usuario") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tema Oscuro", fontSize = 20.sp)
                Switch(
                    checked = esModoOscuro,
                    onCheckedChange = { nuevoValor ->
                        viewModel.setTheme(nuevoValor)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreferenciasScreenPreview() {
    ProyectoFinalTheme {
        PreferenciasScreen()
    }
}