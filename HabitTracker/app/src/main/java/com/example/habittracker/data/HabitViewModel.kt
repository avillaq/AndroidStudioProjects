package com.example.habittracker.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HabitViewModel(private val dao: HabitDao) : ViewModel() {
    val habits = dao.getAllHabits()

    fun agregarHabit(title: String) {
        viewModelScope.launch {
            dao.insertHabit(Habit(title = title))
        }
    }

    fun actualizarHabit(tarea: Habit) {
        viewModelScope.launch {
            dao.updateHabit(tarea)
        }
    }

    fun eliminarHabit(tarea: Habit) {
        viewModelScope.launch {
            dao.deleteHabit(tarea)
        }
    }
}