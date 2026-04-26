package com.example.proyectofinal

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class ToastWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, "Mensaje automático", Toast.LENGTH_SHORT).show()
        }

        // reprogramamos el mismo worker en 5 segundos
        val nextWork = OneTimeWorkRequestBuilder<ToastWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(nextWork)

        return Result.success()
    }
}
