package com.example.habittracker.data

import kotlinx.coroutines.flow.Flow

// 4. Se crea el repositorio
// Es el único punto de contacto entre los datos y la lógica de negocio.
interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>
    suspend fun insert(habit: Habit)
    suspend fun update(habit: Habit)
    suspend fun delete(habit: Habit)
    suspend fun insertLog(log: HabitLog)
    suspend fun deleteLog(habitId: Int, date: Long)
    fun getLogsForHabit(habitId: Int): Flow<List<Long>>
}

class HabitRepositoryImpl(private val dao: HabitDao) : HabitRepository {
    override fun getAllHabits() = dao.getAllHabits()
    override suspend fun insert(habit: Habit) = dao.insertHabit(habit)
    override suspend fun update(habit: Habit) = dao.updateHabit(habit)
    override suspend fun delete(habit: Habit) = dao.deleteHabit(habit)
    override suspend fun insertLog(log: HabitLog) = dao.insertLog(log)
    override suspend fun deleteLog(habitId: Int, date: Long) = dao.deleteLog(habitId, date)
    override fun getLogsForHabit(habitId: Int) = dao.getLogsForHabit(habitId)
}