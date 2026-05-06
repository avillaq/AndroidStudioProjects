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
                AntiPatternScreen()
            }
        }
    }
}

@Composable
fun AntiPatternScreen() {
    var users by remember {
        mutableStateOf(List(1000) { User(it, "User $it") })
    }
    Column {
        Button(onClick = {
            users = users + User(users.size, "Nuevo Usuario")
        }) {
            Text("Agregar usuario")
        }
        LazyColumn {
            items(users) { user ->
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

@Preview
@Composable
fun PreviewScreen() {
    PatternsAppTheme {
        AntiPatternScreen()
    }
}