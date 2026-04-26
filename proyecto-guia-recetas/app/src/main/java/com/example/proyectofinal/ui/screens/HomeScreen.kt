package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Scaffold() { innerPadding ->
        var texto by remember { mutableStateOf("") }

        Column (
            // padding
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            // Elementos centrados
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido",
                fontSize = 24.sp, // tamaño de texto
                fontWeight = FontWeight.Bold // negrita
            )
            Spacer(modifier = Modifier.height(24.dp)) // espaciado
            TextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Escribe algo") } // placeholder
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                if (!texto.isEmpty()) {
                    onNavigate(texto)
                }
            }) {
                Text(text = "Enviar Texto")
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    //HomeScreen()
}