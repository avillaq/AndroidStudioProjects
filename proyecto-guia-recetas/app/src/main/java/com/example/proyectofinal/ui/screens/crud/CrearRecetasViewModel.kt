package com.example.proyectofinal.ui.screens.crud

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectofinal.app
import com.example.proyectofinal.data.Receta
import com.example.proyectofinal.data.RecetasRepository

data class RecetaUiState(
    val nombre: String = "",
    val ingredientes: String = "",
    val tipo: String = ""
)

// Gestiona el estado del formulario  y guarda la nueva receta.
class CrearRecetasViewModel(private val recetasRepository: RecetasRepository) : ViewModel() {

    // El estado de la UI, mutable y observable por la pantalla.
    var uiState by mutableStateOf(RecetaUiState())
        private set

    // Actualizamos el estado de la UI con los nuevos valores introducidos por el usuario.
    fun actualizarUiState(nuevoUiState: RecetaUiState) {
        uiState = nuevoUiState
    }

    // Reseteamos el estado de la UI.
    fun resetUiState() {
        uiState = RecetaUiState()
    }

    suspend fun guardarReceta(): Boolean {
        if (validarEntrada()) {
            recetasRepository.insertarReceta(uiState.toReceta())
            return true
        }
        return false
    }

    // Validamos que los campos no estén vacíos.
    private fun validarEntrada(): Boolean {
        return with(uiState) {
            nombre.isNotBlank() && ingredientes.isNotBlank() && tipo.isNotBlank()
        }
    }

    // Factory
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as app)
                val recetasRepository = application.container.recetasRepository
                CrearRecetasViewModel(recetasRepository = recetasRepository)
            }
        }
    }
}

// Funcion para convertir el estado de la UI en un objeto Receta.
private fun RecetaUiState.toReceta(): Receta = Receta(
    nombre = nombre,
    ingredientes = ingredientes,
    tipo = tipo
)