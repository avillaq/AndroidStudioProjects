package com.example.proyectofinal

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.proyectofinal.data.AppContainer

class App : Application() {

    // Para el manejo de la base de datos
    lateinit var container: AppContainer

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        // Configuración de la base de datos
        container = AppContainer(this)

        // Configuración del canal de notificaciones
        val channel = NotificationChannel(
            "timer_channel",
            "Temporizador",
            NotificationManager.IMPORTANCE_LOW // baja importancia para notificación persistente
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}