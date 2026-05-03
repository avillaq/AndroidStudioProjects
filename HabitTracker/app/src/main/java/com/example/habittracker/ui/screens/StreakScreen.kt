package com.example.habittracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreakScreen(viewModel: HabitViewModel, onBack: () -> Unit) {
    val habits by viewModel.habits.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Rachas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (habits.isEmpty()) {
            Box(Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center) {
                Text("Aun no hay rachas")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(habits) { habit ->
                    StreakCard(habit)
                }
            }
        }
    }
}


@Composable
fun StreakCard(habit: Habit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = habit.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = if (habit.streakCount > 0) "¡Sigue asi!" else "¡Completa un hábito hoy!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.fire),
                    contentDescription = "Fuego de racha",
                    tint = if (habit.streakCount > 0) Color(0xFFFF5722) else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "${habit.streakCount}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (habit.streakCount > 0) Color(0xFFFF5722) else Color.Gray
                )
            }
        }
    }
}