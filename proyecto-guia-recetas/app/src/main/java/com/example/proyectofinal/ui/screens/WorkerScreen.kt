package com.example.proyectofinal.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.proyectofinal.ToastWorker
import java.util.UUID

@Composable
fun WorkerScreen(context: Context) {
    var isAutoActive by remember { mutableStateOf(false) }
    var currentWorkId by remember { mutableStateOf<UUID?>(null) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button (onClick = {
            Toast.makeText(context, "Mensaje manual", Toast.LENGTH_SHORT).show()
        }) {
            Text("Mensaje Manual")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isAutoActive) {
                val workRequest = OneTimeWorkRequestBuilder<ToastWorker>().build()
                currentWorkId = workRequest.id
                WorkManager.getInstance(context).enqueue(workRequest)
                isAutoActive = true
            } else {
                currentWorkId?.let {
                    WorkManager.getInstance(context).cancelWorkById(it)
                }
                isAutoActive = false
            }
        }) {
            Text(if (isAutoActive) "Desactivar Automático" else "Activar Automático")
        }
    }
}

@Preview
@Composable
fun WorkerScreenPreview() {
    WorkerScreen(context = LocalContext.current)
}