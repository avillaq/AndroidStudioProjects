package com.example.proyectofinal.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

const val THEME_TYPE = "theme_type"
enum class ThemeType { LIGHT, DARK }

class ThemeRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val themeType = intPreferencesKey(THEME_TYPE)
    }

    val themeTypeActual: Flow<ThemeType> = dataStore.data.map { preferences ->
        ThemeType.entries[preferences[themeType] ?: 0]
    }

    fun setThemeType(value: ThemeType, scope: CoroutineScope) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[themeType] = value.ordinal
            }
        }
    }
}