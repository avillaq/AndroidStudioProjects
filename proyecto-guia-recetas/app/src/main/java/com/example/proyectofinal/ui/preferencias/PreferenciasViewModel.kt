package com.example.proyectofinal.ui.preferencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.store.ThemeRepository
import com.example.proyectofinal.store.ThemeType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PreferenciasViewModel(private val themeRepository: ThemeRepository) : ViewModel() {

    val themeType: StateFlow<ThemeType> = themeRepository.themeTypeActual
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeType.LIGHT
        )

    fun setTheme(esOscuro: Boolean) {
        val nuevoTema = if (esOscuro) ThemeType.DARK else ThemeType.LIGHT
        themeRepository.setThemeType(nuevoTema, viewModelScope)
    }
}

class PreferenciasViewModelFactory(private val repository: ThemeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferenciasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PreferenciasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}