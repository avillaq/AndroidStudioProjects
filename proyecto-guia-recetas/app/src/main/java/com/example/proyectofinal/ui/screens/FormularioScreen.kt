package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormularioScreen(onNavigate: (String) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }

    Scaffold() { innerPadding ->
        Column (
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registrate para continuar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            /* Campo para el nombre */
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                placeholder = { Text("Ingrese su nombre")}
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* Campo para el Correo */
            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electronico") },
                placeholder = { Text("Ingrese su correo electronico")}
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button (onClick = {
                    nombre = ""
                    correo = ""
                }) {
                    Text("Cancelar")
                }
                Button (onClick = {
                    if (!nombre.isEmpty() && !correo.isEmpty()) {
                        val mensaje = "Nombre: $nombre\nCorreo: $correo"
                        onNavigate(mensaje)
                    }
                }) {
                    Text("Registrarse")
                }
            }
        }
    }
}

@Preview
@Composable
fun FormularioScreenPreview() {
    //FormularioScreen()
}