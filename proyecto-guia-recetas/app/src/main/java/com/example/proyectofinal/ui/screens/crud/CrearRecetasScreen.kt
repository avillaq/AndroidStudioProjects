package com.example.proyectofinal.ui.screens.crud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRecetasScreen (
    onNavigateToBack: () -> Unit,
    viewModel: CrearRecetasViewModel = viewModel(factory = CrearRecetasViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar (
                title = { Text("Crear Recetas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateToBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*
            Formulario simple para crar recetas
            */
            Spacer(modifier = Modifier.height(16.dp))

            // nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.actualizarUiState(uiState.copy(nombre = it)) },
                label = { Text("Nombre de la receta") },
                modifier = Modifier.fillMaxWidth()
            )

            // ingredientes
            OutlinedTextField(
                value = uiState.ingredientes,
                onValueChange = { viewModel.actualizarUiState(uiState.copy(ingredientes = it)) },
                label = { Text("Ingredientes") },
                modifier = Modifier.fillMaxWidth()
            )

            // tipo
            OutlinedTextField(
                value = uiState.tipo,
                onValueChange = { viewModel.actualizarUiState(uiState.copy(tipo = it)) },
                label = { Text("Tipo (Ejemplo: Almuerzo, Postre)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f)) // Expande el espacio disponible

            // guardamos la receta
            Button(
                onClick = {
                    coroutineScope.launch {
                        val exito: Boolean = viewModel.guardarReceta()
                        if (exito) {
                            viewModel.resetUiState()
                            snackbarHostState.showSnackbar("Receta guardada exitosamente")
                        } else {
                            snackbarHostState.showSnackbar("Compruebe que los campos no esten vacios")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Receta")
            }
        }
    }
}
