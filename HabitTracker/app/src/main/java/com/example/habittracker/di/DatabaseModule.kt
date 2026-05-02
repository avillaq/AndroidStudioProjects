package com.example.habittracker.di

import android.content.Context
import com.example.habittracker.data.HabitDao
import com.example.habittracker.data.HabitDatabase
import com.example.habittracker.data.HabitRepository
import com.example.habittracker.data.HabitRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 6. Hilt Module
// Aquí se enseña a Hilt cómo fabricar el Database, el Dao y el Repository.
// Si no se hace esto primero, el ViewModel no compilará porque no sabrá de dónde sacar el Repository.
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HabitDatabase {
        return HabitDatabase.getDatabase(context)
    }

    @Provides
    fun provideHabitDao(database: HabitDatabase): HabitDao {
        return database.habitDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: HabitDao): HabitRepository {
        return HabitRepositoryImpl(dao)
    }
}