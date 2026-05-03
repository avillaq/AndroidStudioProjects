package com.example.habittracker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// 1. Se crea la entidad
// Define qué es un hábito. Sin el objeto, nada existe.
@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val isCompletedToday: Boolean = false,
    val lastCompletedDate: Long? = null,
    val streakCount: Int = 0
)

// Esta entidad guardará cada vez que un hábito se completa en una fecha específica.
@Entity(
    tableName = "habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("habitId")]
)
data class HabitLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val habitId: Int,
    val date: Long // Timestamp del inicio del día (00:00:00)
)