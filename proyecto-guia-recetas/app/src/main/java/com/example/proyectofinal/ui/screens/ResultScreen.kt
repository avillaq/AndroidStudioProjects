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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ResultScreen(mensaje : String, onNavigate : () -> Unit) {
    Scaffold() { innerPadding ->

        Column (
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tu texto es:",
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = mensaje,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { onNavigate() }) {
                Text(text = "Atras")
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun PreviewResultScreen() {
    //ResultScreen()
}