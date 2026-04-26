package com.example.proyectofinal.ui.screens.crud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectofinal.app
import com.example.proyectofinal.data.RecetasRepository


class AccionesAdminViewModel(private val recetasRepository: RecetasRepository) : ViewModel() {

    suspend fun eliminarTodasRecetas() {
        recetasRepository.eliminarTodasRecetas()
    }

    companion object {
        /**
         * Factory para crear una instancia de AccionesAdminViewModel.
         * Utiliza el nuevo inicializador de 'viewModelFactory' para simplificar la creación.
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as app)
                val recetasRepository = application.container.recetasRepository
                // Crea y devuelve la instancia del ViewModel
                AccionesAdminViewModel(recetasRepository = recetasRepository)
            }
        }
    }
}