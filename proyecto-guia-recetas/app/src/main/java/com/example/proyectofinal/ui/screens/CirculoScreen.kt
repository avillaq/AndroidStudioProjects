package com.example.proyectofinal.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CirculoScreen() {
    val radioMinimo = 100f
    val radioMaximo = 450f
    var radioActual by remember { mutableFloatStateOf(radioMinimo) }

    var isPresionado by remember { mutableStateOf(false) }
    val rutina = rememberCoroutineScope()
    val escala by animateFloatAsState(
        targetValue = if (isPresionado) 0.8f else 1f,
        animationSpec = spring(
            dampingRatio = 0.4f,
            stiffness = Spring.StiffnessLow
        )
    )

    Scaffold() { innerPadding ->
        Column (
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // circulo
            Canvas (modifier = Modifier.size(100.dp).scale(escala)) {
                drawCircle(Color.Red, radius = radioActual)
            }

            // botones
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button (onClick = { // animar circulo
                    isPresionado = !isPresionado
                    rutina.launch {
                        delay(200)
                        isPresionado = false
                    }
                }) {
                    Text(text = "Animar", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button (onClick = { // reducir tamaño
                        if (radioActual > radioMinimo) {
                            radioActual = radioActual - 30f
                        }
                    }) {
                        Text(text = "-", fontSize = 24.sp)
                    }
                    Button (onClick = { // aumentar tamaño
                        if (radioActual < radioMaximo) {
                            radioActual = radioActual + 30f
                        }
                    }) {
                        Text("+", fontSize = 24.sp)
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun CirculoScreenPreview() {
    CirculoScreen()
}

