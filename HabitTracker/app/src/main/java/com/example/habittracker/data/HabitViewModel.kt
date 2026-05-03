package com.example.habittracker.data

import java.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

// 7. Se crea el ViewModel.
// Toma los datos del Repositorio y los prepara para la UI.
@HiltViewModel
class HabitViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {
    val habits = repository.getAllHabits().map { lista ->
        val today = getStartOfDayMillis(0)
        lista.map { habit ->
            // Si el último check no fue hoy, forzamos isCompletedToday a false para la UI
            if (habit.lastCompletedDate != today && habit.isCompletedToday) {
                habit.copy(isCompletedToday = false)
            } else {
                habit
            }
        }
    }

    fun agregarHabit(title: String) {
        viewModelScope.launch {
            repository.insert(Habit(title = title))
        }
    }

    fun toggleHabitCompletion(habit: Habit) {
        viewModelScope.launch {
            val today = getStartOfDayMillis(0)
            val yesterday = getStartOfDayMillis(-1)

            if (!habit.isCompletedToday) {
                // cuando el habito es completado (racha)
                val newStreak = when (habit.lastCompletedDate) {
                    yesterday -> habit.streakCount + 1 // marca primera vez hoy, sube la racha
                    today -> habit.streakCount // en caso de que ya se haya marcado hoy, se mantiene (no creo que pase)
                    else -> 1 // aqui cuando se rompe la racha o es un nuevo habito
                }
                repository.update(habit.copy(
                    isCompletedToday = true,
                    lastCompletedDate = today,
                    streakCount = newStreak
                ))

                // se guarda en el historial
                repository.insertLog(HabitLog(habitId = habit.id, date = today))
            } else {
                // cuando se desmarca un completado (Racha)
                repository.update(habit.copy(
                    isCompletedToday = false,
                    streakCount = (habit.streakCount - 1).coerceAtLeast(0),
                    lastCompletedDate = yesterday
                ))

                // se elimina del historial
                repository.deleteLog(habit.id, today)
            }
        }
    }

    fun eliminarHabit(habit: Habit) {
        viewModelScope.launch {
            repository.delete(habit)
        }
    }

    fun getLogsForHabit(habitId: Int) = repository.getLogsForHabit(habitId)

    // obtiene el timestamp de "hoy" a las 00:00:00
    private fun getStartOfDayMillis(daysOffset: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysOffset)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}