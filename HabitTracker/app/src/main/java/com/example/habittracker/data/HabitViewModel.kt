package com.example.habittracker.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// 7. Se crea el ViewModel.
// Toma los datos del Repositorio y los prepara para la UI.
@HiltViewModel
class HabitViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {
    val habits = repository.getAllHabits()

    fun agregarHabit(title: String) {
        viewModelScope.launch {
            repository.insert(Habit(title = title))
        }
    }

    fun actualizarHabit(habit: Habit) {
        viewModelScope.launch {
            repository.update(habit)
        }
    }

    fun eliminarHabit(habit: Habit) {
        viewModelScope.launch {
            repository.delete(habit)
        }
    }
}