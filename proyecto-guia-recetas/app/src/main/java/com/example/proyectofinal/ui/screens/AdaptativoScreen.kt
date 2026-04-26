package com.example.proyectofinal.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AdaptativoScreen() {
        Scaffold() { innerPadding ->
            Column(
                // padding
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                // Elementos centrados
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bienvenido",
                    style = MaterialTheme.typography.headlineMedium, // Respeta tamaño de fuente del sistema
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {}) {
                    Text(text = "Mi boton")
                }
            }
        }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAdaptativo() {
    AdaptativoScreen();
}