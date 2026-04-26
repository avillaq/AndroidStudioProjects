package com.example.todoapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TareaViewModel(private val dao: TareaDao) : ViewModel() {
    val tareas = dao.getAllTareas()

    fun agregarTarea(titulo: String) {
        viewModelScope.launch {
            dao.insertTarea(Tarea(titulo = titulo))
        }
    }

    fun actualizarTarea(tarea: Tarea) {
        viewModelScope.launch {
            dao.updateTarea(tarea)
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            dao.deleteTarea(tarea)
        }
    }
}