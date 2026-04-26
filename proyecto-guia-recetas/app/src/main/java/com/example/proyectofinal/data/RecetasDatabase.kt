package com.example.proyectofinal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Receta::class], version = 1, exportSchema = false)
abstract class RecetasDatabase : RoomDatabase() {
    abstract fun recetaDao(): RecetaDao

    companion object {
        @Volatile
        private var Instance: RecetasDatabase? = null

        fun getDatabase(context: Context): RecetasDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    RecetasDatabase::class.java,
                    "recetas_db"
                )
                    .build()
                    .also { Instance = it }
            }

        }
    }
}
