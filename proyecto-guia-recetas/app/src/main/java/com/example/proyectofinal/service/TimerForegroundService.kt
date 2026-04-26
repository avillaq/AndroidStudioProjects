package com.example.proyectofinal.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.proyectofinal.MainActivity
import com.example.proyectofinal.R

class TimerForegroundService : Service() {

    private val binder = LocalBinder()
    private var startTimeMillis: Long = 0L
    private var isRunning = false
    private val timerHandler = Handler(Looper.getMainLooper())
    private lateinit var notificationBuilder: NotificationCompat.Builder

    private val tickRunnable = object : Runnable {
        override fun run() {
            if (!isRunning) return
            val elapsed = System.currentTimeMillis() - startTimeMillis
            updateNotification(elapsed)
            timerHandler.postDelayed(this, 1000L)
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): TimerForegroundService = this@TimerForegroundService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            startTimer()
        }
        return START_STICKY // El servicio se reiniciará si el sistema lo mata
    }

    private fun startTimer() {
        isRunning = true
        startTimeMillis = System.currentTimeMillis()
        startForeground(NOTIFICATION_ID, buildInitialNotification())
        timerHandler.post(tickRunnable)
    }

    fun stopTimer() {
        isRunning = false
        timerHandler.removeCallbacks(tickRunnable)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun buildInitialNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        notificationBuilder = NotificationCompat.Builder(this, "timer_channel")
            .setContentTitle("Temporizador en curso")
            .setContentText("00:00")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Asegúrate que este icono exista
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        return notificationBuilder.build()
    }

    private fun updateNotification(elapsedMillis: Long) {
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / 1000) / 60
        val text = String.format("%02d:%02d", minutes, seconds)

        // Reutilizamos el builder y solo actualizamos el texto
        notificationBuilder.setContentText(text)

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        // Es una buena práctica usar una constante para el ID de la notificación
        private const val NOTIFICATION_ID = 1
    }
}