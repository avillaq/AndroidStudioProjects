package com.example.proyectofinal.data

import kotlinx.coroutines.flow.Flow

class RecetasRepository (private val recetaDao: RecetaDao){

    val obtenerTodasRecetas: Flow<List<Receta>> = recetaDao.obtenerRecetas()

    suspend fun obtenerRecetaPorId(id: Int): Receta? {
        return recetaDao.obtenerRecetaPorId(id)
    }

    suspend fun insertarReceta(receta: Receta) {
        recetaDao.insertarReceta(receta)
    }

    suspend fun actualizarReceta(receta: Receta) {
        recetaDao.actualizarReceta(receta)
    }

    suspend fun eliminarReceta(receta: Receta) {
        recetaDao.eliminarReceta(receta)
    }

    suspend fun eliminarTodasRecetas() {
        recetaDao.eliminarTodas()
    }
}