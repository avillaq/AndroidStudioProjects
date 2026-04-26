package com.example.proyectofinal.ui.screens

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.proyectofinal.service.TimerForegroundService
import android.Manifest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimerScreen() {
    val context = LocalContext.current
    var isRunning by remember { mutableStateOf(false) }
    var timerService by remember { mutableStateOf<TimerForegroundService?>(null) }
    var isBound by remember { mutableStateOf(false) }

    val connection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as TimerForegroundService.LocalBinder
                timerService = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                timerService = null
                isBound = false
            }
        }
    }

    // Gestiona el ciclo de vida del servicio (bind/unbind)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, connection) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                Intent(context, TimerForegroundService::class.java).also { intent ->
                    context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
                }
            } else if (event == Lifecycle.Event.ON_STOP) {
                if (isBound) {
                    context.unbindService(connection)
                    isBound = false
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            if (isBound) {
                context.unbindService(connection)
            }
        }
    }

    // Lanzador para el permiso de notificaciones
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permiso concedido, iniciar el servicio
            val intent = Intent(context, TimerForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            isRunning = true
        } else {
            // Permiso denegado, podrías mostrar un mensaje al usuario
        }
    }

    val startService = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            val intent = Intent(context, TimerForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            isRunning = true
        }
    }

    val stopService = {
        timerService?.stopTimer()
        isRunning = false
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { startService() },
            enabled = !isRunning
        ) {
            Text(if (isRunning) "Temporizador activo" else "Iniciar temporizador")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { stopService() }, enabled = isRunning) {
            Text("Detener temporizador")
        }
    }
}

@Preview
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}