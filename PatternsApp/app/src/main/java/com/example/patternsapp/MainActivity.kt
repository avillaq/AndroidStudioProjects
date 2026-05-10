package com.example.patternsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patternsapp.ui.theme.PatternsAppTheme

data class User(val id: Int, val name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatternsAppTheme {
                OptimizedModifiersScreen()
            }
        }
    }
}

// Fase 0
@Composable
fun AntiPatternScreen() {
    var users by remember {
        mutableStateOf(List(1000) { User(it, "User $it") })
    }
    Column (
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Button(onClick = {
            users = users + User(users.size, "Nuevo Usuario")
        }) {
            Text("Agregar usuario")
        }
        LazyColumn {
            items(users) { user ->
                val expensiveCalculation = List(20000) {
                    kotlin.math.sqrt(it.toDouble())
                }.sum()
                Text(
                    text = user.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(16.dp)
                )
            }
        }
    }
}


// Fase 1
@Composable
fun KeysImprovedScreen() {
    var users by remember {
        mutableStateOf(List(1000) { User(it, "User $it") })
    }
    Column (
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Button(onClick = {
            users = users + User(users.size, "Nuevo Usuario")
        }) {
            Text("Agregar usuario")
        }
        LazyColumn {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                val expensiveCalculation = List(20000) {
                    kotlin.math.sqrt(it.toDouble())
                }.sum()
                Text(
                    text = user.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(16.dp)
                )
            }
        }
    }
}


// Fase 2
@Composable
fun StatePerItemScreen() {
    var users by remember {
        mutableStateOf(List(1000) { User(it, "User $it") })
    }
    Column (
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Button(onClick = {
            users = users + User(users.size, "Nuevo Usuario")
        }) {
            Text("Agregar usuario")
        }
        LazyColumn {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                UserItem(user)
            }
        }
    }
}


@Composable
fun UserItem(user: User) {
    var expanded by remember {
        mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        val expensiveCalculation = List(20000) {
            kotlin.math.sqrt(it.toDouble())
        }.sum()
        Text(user.name)
        if (expanded) {
            Text("Detalles del usuario ${user.id}")
        }
    }
}


// Fase 3
@Composable
fun SnapshotStateListScreen() {
    val users = remember {
        mutableStateListOf<User>().apply {
            addAll(List(1000) { User(it, "User $it") })
        }
    }
    Column (
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Button(onClick = {
            users.add(User(users.size, "Nuevo Usuario"))
        }) {
            Text("Agregar usuario")
        }
        LazyColumn {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                UserItem(user)
            }
        }
    }
}



// Fase 4
@Composable
fun OptimizedModifiersScreen() {
    val users = remember {
        mutableStateListOf<User>().apply {
            addAll(List(1000) { User(it, "User $it") })
        }
    }
    val itemModifier = remember {
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    }
    Column (
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Button(onClick = {
            users.add(User(users.size, "Nuevo Usuario"))
        }) {
            Text("Agregar usuario")
        }
        LazyColumn {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                UserItemOptimized(user, itemModifier)
            }
        }
    }
}

@Composable
fun UserItemOptimized(user: User, modifier: Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.clickable {
            expanded = !expanded
        }
    ) {
        val expensiveCalculation = List(20000) {
            kotlin.math.sqrt(it.toDouble())
        }.sum()
        Text(user.name)
        if (expanded) {
            Text("Detalles del usuario ${user.id}")
        }
    }
}


// Fase 5
@Composable
fun FinalOptimizedScreen() {
    val users = remember {
        mutableStateListOf<User>().apply {
            addAll(List(1000) { User(it, "User $it") })
        }
    }
    val itemModifier = remember {
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    }
    Column (
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Button(onClick = {
            users.add(User(users.size, "Nuevo Usuario"))
        }) {
            Text("Agregar usuario")
        }
        LazyColumn {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                UserItemOptimized(user, itemModifier)
            }
        }
    }
}