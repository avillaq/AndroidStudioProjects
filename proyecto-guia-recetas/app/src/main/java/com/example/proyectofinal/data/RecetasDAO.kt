package com.example.proyectofinal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReceta(receta: Receta)

    @Update
    suspend fun actualizarReceta(receta: Receta)

    @Delete
    suspend fun eliminarReceta(receta: Receta)

    @Query("SELECT * FROM recetas WHERE id = :id")
    suspend fun obtenerRecetaPorId(id: Int): Receta?

    @Query("SELECT * FROM recetas ORDER BY nombre ASC")
    fun obtenerRecetas(): Flow<List<Receta>>

    @Query("DELETE FROM recetas")
    suspend fun eliminarTodas()
}
