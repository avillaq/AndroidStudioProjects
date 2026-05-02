package com.example.habittracker.data

import androidx.room.Entity
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