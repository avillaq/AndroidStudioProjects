package com.example.habittracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitViewModel

enum class FiltroHabit { TODOS, COMPLETADOS, NO_COMPLETADOS }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
        viewModel: HabitViewModel,
        onNavigateToStreaks: () -> Unit,
        onNavigateToHistory: (Int) -> Unit
    ) {
    var input by  remember { mutableStateOf("") }
    val habits by viewModel.habits.collectAsState(initial = emptyList())

    // Filtros
    var filtroActual by remember { mutableStateOf(FiltroHabit.TODOS) }
    var habitsFiltrados = when (filtroActual) {
        FiltroHabit.TODOS -> habits
        FiltroHabit.COMPLETADOS -> habits.filter { it.isCompletedToday }
        FiltroHabit.NO_COMPLETADOS -> habits.filter { !it.isCompletedToday }
    }

    // Cálculo derivado (recomposición inteligente)
    val completedCount = habits.count { it.isCompletedToday }
    val progress = if (habits.isNotEmpty()) { completedCount.toFloat() / habits.size
    } else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Tracker") },
                actions = {
                    IconButton(onClick = onNavigateToStreaks) {
                        Icon(painter = painterResource(id = R.drawable.fire), contentDescription = "Rachas", tint = Color(0xFFFF5722))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Barra de progreso
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Progreso: ${
                    (progress *
                            100).toInt()
                }%"
            )
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
            Spacer(modifier = Modifier.height(8.dp))

            SeccionFiltros(
                filtroActual = filtroActual,
                onFiltroChange = { filtroActual = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (habitsFiltrados.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No hay habitos",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(habitsFiltrados, key = { it.id }) { habit ->
                        HabitItem(
                            habit = habit,
                            onToggle = { checked ->
                                viewModel.toggleHabitCompletion(habit)
                            },
                            onDelete = {
                                viewModel.eliminarHabit(habit)
                            },
                            onNavigateToHistory = onNavigateToHistory
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HabitItem(
    habit: Habit,
    onToggle: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onNavigateToHistory: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onNavigateToHistory(habit.id) }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = habit.isCompletedToday,
                onCheckedChange = onToggle
            )
            Text(
                text = habit.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (habit.isCompletedToday) TextDecoration.LineThrough else TextDecoration.None,
                ),
                color = if (habit.isCompletedToday) Color(0xFF4CAF50).copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
            )
            Button(onClick = onDelete) {
                Text("Eliminar")
            }
        }
    }

}

@Composable
fun SeccionFiltros(filtroActual: FiltroHabit, onFiltroChange: (FiltroHabit) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FiltroHabit.entries.forEach { filtro ->
            val seleccionado = filtro == filtroActual
            FilterChip(
                selected = seleccionado,
                onClick = { onFiltroChange(filtro) },
                label = { Text(filtro.name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " ")) },
                leadingIcon = if (seleccionado) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp) ) }
                } else null
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewApp() {
//    MaterialTheme {
//        HomeScreen(viewModel)
//    }
//}