package com.example.proyectofinal.data

import android.content.Context

class AppContainer(private val context: Context)  {
    // "lazy" (solo cuando se necesita por primera vez)
    val recetasRepository: RecetasRepository by lazy {
        RecetasRepository(RecetasDatabase.getDatabase(context).recetaDao())
    }
}