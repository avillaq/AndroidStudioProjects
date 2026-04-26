package com.example.todoapp

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.TareaDatabase
import com.example.todoapp.data.TareaViewModel
import com.example.todoapp.data.Tarea
import com.example.todoapp.ui.theme.TodoAppTheme

enum class FiltroTarea { TODAS, PENDIENTES, COMPLETADAS }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = TareaDatabase.getDatabase(applicationContext)
        val viewModel = TareaViewModel(db.tareaDao())
        setContent {
            TodoAppTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppTareas(viewModel)
                }
            }
        }
    }
}

@Composable
fun TituloApp() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gestor de Tareas",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
fun BotonPrimario(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(texto)
    }
}

@Composable
fun AppTareas(viewModel: TareaViewModel) {
    val tareas by viewModel.tareas.collectAsState(initial = emptyList())
    var texto by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var tareaSeleccionada by remember { mutableStateOf<Tarea?>(null) }
    var textoEdicion by remember { mutableStateOf("") }

    var filtroActual by remember { mutableStateOf(FiltroTarea.TODAS) }

    val tareasFiltradas = when (filtroActual) {
        FiltroTarea.TODAS -> tareas
        FiltroTarea.PENDIENTES -> tareas.filter { !it.completada }
        FiltroTarea.COMPLETADAS -> tareas.filter { it.completada }
    }

    // modal para editar
    if (showDialog && tareaSeleccionada != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                    CampoTexto(
                        valor = textoEdicion,
                        onValorChange = { textoEdicion = it },
                        label = "Editar tarea"
                    )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.actualizarTarea(tareaSeleccionada!!.copy(titulo = textoEdicion))
                    showDialog = false
                }) {
                    Text("GUARDAR")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("CANCELAR")
                }
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        TituloApp()
        Spacer(modifier = Modifier.height(16.dp))
        CampoTexto(
            valor = texto,
            onValorChange = { texto = it },
            label = "Nueva tarea"
        )
        Spacer(modifier = Modifier.height(8.dp))
        BotonPrimario("Agregar tarea") {
            if (texto.isNotBlank()) {
                viewModel.agregarTarea(texto)
                texto = ""
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        SeccionFiltros(
            filtroActual = filtroActual,
            onFiltroChange = { filtroActual = it }
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (tareasFiltradas.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay tareas",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            ListaTareas(
                tareas = tareasFiltradas,
                onToggle = { tarea ->
                    viewModel.actualizarTarea(tarea.copy(completada = !tarea.completada))
                },
                onDelete = { tarea ->
                    viewModel.eliminarTarea(tarea)
                },
                onEdit = { tarea ->
                    tareaSeleccionada = tarea
                    textoEdicion = tarea.titulo
                    showDialog = true
                }
            )
        }
    }
}

@Composable
fun CampoTexto(
    valor: String,
    onValorChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth()
    )
}
@Composable
fun ListaTareas(
    tareas: List<Tarea>,
    onToggle: (Tarea) -> Unit,
    onDelete: (Tarea) -> Unit,
    onEdit: (Tarea) -> Unit
) {
    LazyColumn {
        items(tareas) { tarea ->
            ItemTarea(
                tarea = tarea,
                onToggle = { onToggle(tarea) },
                onDelete = { onDelete(tarea) },
                onEdit = { onEdit(tarea) }
            )
        }
    }
}
@Composable
fun ItemTarea(
    tarea: Tarea,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    TarjetaBase {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = { onToggle() }
            )
            Text(
                text = tarea.titulo,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f),
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (tarea.completada) TextDecoration.LineThrough else TextDecoration.None
                ),
                color = if (tarea.completada) Color.Gray else MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = onEdit) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun TarjetaBase(
    modifier: Modifier = Modifier,
    contenido: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        contenido()
    }
}

@Composable
fun SeccionFiltros(filtroActual: FiltroTarea, onFiltroChange: (FiltroTarea) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FiltroTarea.entries.forEach { filtro ->
            val seleccionado = filtro == filtroActual
            FilterChip(
                selected = seleccionado,
                onClick = { onFiltroChange(filtro) },
                label = { Text(filtro.name.lowercase().replaceFirstChar { it.uppercase() }) },
                leadingIcon = if (seleccionado) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                } else null
            )
        }
    }
}

@Preview(showBackground = true, name = "Modo Claro")
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true,
//    name = "Modo Oscuro"
//)
@Composable
fun PreviewApp() {
    TodoAppTheme {
        val context = LocalContext.current
        val db = TareaDatabase.getDatabase(context)
        val viewModel = TareaViewModel(db.tareaDao())
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppTareas(viewModel)
        }
    }
}
