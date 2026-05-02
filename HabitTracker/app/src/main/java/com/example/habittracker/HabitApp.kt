package com.example.habittracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// 5. Aplicacion para Hilt
// Necesaria para que Hilt sepa dónde arrancar.
@HiltAndroidApp
class HabitApp : Application()