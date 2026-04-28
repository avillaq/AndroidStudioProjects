package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitDatabase
import com.example.habittracker.data.HabitViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = HabitDatabase.getDatabase(applicationContext)
        val viewModel = HabitViewModel(db.habitDao())
        setContent {
            MaterialTheme{
                HabitTrackerApp(viewModel)
            }
        }
    }
}

@Composable
fun TituloApp() {
    Text(
        text = "Gestor de Tareas",
        style =
            MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
}
@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    MaterialTheme {
        val context = LocalContext.current
        val db = HabitDatabase.getDatabase(context)
        val viewModel = HabitViewModel(db.habitDao())
        HabitTrackerApp(viewModel)
    }
}


@Composable
fun HabitItem(
    habit: Habit,
    onToggle: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Checkbox(
                checked = habit.isCompletedToday,
                onCheckedChange = onToggle
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = habit.title,
                style = if (habit.isCompletedToday)
                    MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                else
                    MaterialTheme.typography.bodyLarge
            )
        }
        Button(onClick = onDelete) {
            Text("Eliminar")
        }
    }
}

@Composable
fun HabitTrackerApp(viewModel: HabitViewModel) {
    var input by  remember { mutableStateOf("") }
    val habits by viewModel.habits.collectAsState(initial = emptyList())
    // Cálculo derivado (recomposición inteligente)
    val completedCount = habits.count { it.isCompletedToday }
    val progress = if (habits.isNotEmpty()) { completedCount.toFloat() / habits.size
    } else 0f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Habit Tracker",
            style =
                MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Barra de progreso
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Progreso: ${(progress *
                100).toInt()}%")
        Spacer(modifier = Modifier.height(16.dp))
        // Input
        Row {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Nuevo hábito") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (input.isNotBlank()) {
                    viewModel.agregarHabit(input)
                    input = ""
                }
            }) {
                Text("Agregar")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(habits, key = { it.id }) { habit ->
                HabitItem(
                    habit = habit,
                    onToggle = { checked ->
                        viewModel.actualizarHabit(habit.copy(isCompletedToday = checked))
                    },
                    onDelete = {
                        viewModel.eliminarHabit(habit)
                    }
                )
            }
        }
    }
}