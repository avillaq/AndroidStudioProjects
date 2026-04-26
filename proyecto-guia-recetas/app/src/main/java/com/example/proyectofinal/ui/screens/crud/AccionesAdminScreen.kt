package com.example.proyectofinal.ui.screens.crud

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
fun AccionesAdminScreen (
    onNavigateToCrear: () -> Unit,
    onNavigateToLista: () -> Unit,
    viewModel: AccionesAdminViewModel = viewModel(factory = AccionesAdminViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar (
                title = { Text("Acciones de Administrador") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*
            Botones de accion
            */
            Button(
                onClick = onNavigateToCrear,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Nueva Receta")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToLista,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Todas las Recetas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.eliminarTodasRecetas()
                        snackbarHostState.showSnackbar("Todas las recetas han sido eliminadas")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar Todas las Recetas")
            }
        }
    }
}