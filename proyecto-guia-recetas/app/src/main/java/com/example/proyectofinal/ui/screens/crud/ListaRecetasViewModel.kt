package com.example.proyectofinal.ui.screens.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectofinal.app
import com.example.proyectofinal.data.Receta
import com.example.proyectofinal.data.RecetasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// lista de recetas a mostrar.
data class ListaRecetasUiState(
    val listaRecetas: List<Receta> = listOf()
)

class ListaRecetasViewModel(recetasRepository: RecetasRepository) : ViewModel() {

    // estado de la UI que contiene la lista de recetas
    val uiState: StateFlow<ListaRecetasUiState> =
        recetasRepository.obtenerTodasRecetas // flow del repositorio
            .map { recetas -> ListaRecetasUiState(recetas) } // Lo transformamos a nuestro objeto de estado
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L), // El flujo se activa cuando la UI está visible
                initialValue = ListaRecetasUiState() // Valor inicial mientras carga
            )

    // factory
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as app)
                val recetasRepository = application.container.recetasRepository
                ListaRecetasViewModel(recetasRepository)
            }
        }
    }
}