package com.example.proyectofinal.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class Receta(
    val nombre: String,
    val ingredientes: String,
    val tipo: String,
    val imagen: String
)

val recetas = listOf(
    Receta("Lomo Saltado", "Carne, cebolla, tomate, papas", "Almuerzo", "https://www.themealdb.com/images/media/meals/utxqpt1511639216.jpg"),
    Receta("Ceviche", "Pescado, limón, cebolla, ají", "Almuerzo", "https://www.themealdb.com/images/media/meals/1548772327.jpg"),
    Receta("Ají de Gallina", "Pollo, ají amarillo, pan, leche", "Almuerzo", "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg"),
    Receta("Tallarines Verdes", "Pasta, albahaca, espinaca, queso", "Cena", "https://picsum.photos/200?random=1"),
    Receta("Arroz con Leche", "Arroz, leche, canela, azúcar", "Postre", "https://picsum.photos/200?random=2"),
    Receta("Anticuchos", "Corazón de res, ají panca, vinagre", "Cena", "https://picsum.photos/200?random=3"),
    Receta("Papa a la Huancaína", "Papa, ají amarillo, queso, leche", "Entrada", "https://picsum.photos/200?random=4"),
    Receta("Pollo al Horno", "Pollo, ajo, romero, papas", "Almuerzo", "https://picsum.photos/200?random=5"),
    Receta("Chicha Morada", "Maíz morado, piña, canela, clavo", "Bebida", "https://picsum.photos/200?random=6"),
    Receta("Mazamorra Morada", "Maíz morado, frutas, canela", "Postre", "https://picsum.photos/200?random=7"),
    Receta("Panqueques", "Harina, huevo, leche, mantequilla", "Desayuno", "https://www.themealdb.com/images/media/meals/rwuyqx1511383174.jpg"),
    Receta("Ensalada de Quinoa", "Quinoa, tomate, pepino, limón", "Cena", "https://picsum.photos/200?random=8"),
    Receta("Tamal", "Maíz, carne, ají, aceituna", "Desayuno", "https://picsum.photos/200?random=9"),
    Receta("Empanadas", "Harina, carne, cebolla, huevo", "Entrada", "https://www.themealdb.com/images/media/meals/rwuyqx1511383174.jpg"),
    Receta("Sopa Criolla", "Fideos, carne, leche, huevo", "Cena", "https://picsum.photos/200?random=10"),
    Receta("Chocotejas", "Chocolate, manjar, nueces", "Postre", "https://picsum.photos/200?random=11"),
    Receta("Juane", "Arroz, pollo, huevo, hoja de bijao", "Almuerzo", "https://picsum.photos/200?random=12"),
    Receta("Torta de Chocolate", "Harina, cacao, huevo, azúcar", "Postre", "https://www.themealdb.com/images/media/meals/rwuyqx1511383174.jpg"),
    Receta("Leche Asada", "Leche, huevo, azúcar, vainilla", "Postre", "https://picsum.photos/200?random=13"),
    Receta("Sanguchón de Chicharrón", "Pan, chicharrón, camote, salsa criolla", "Desayuno", "https://picsum.photos/200?random=14"),
    Receta("Ocopa Arequipeña", "Papa, huacatay, maní, queso", "Entrada", "https://picsum.photos/200?random=15"),
    Receta("Zarza de Patitas", "Patitas de cerdo, cebolla, ají", "Almuerzo", "https://picsum.photos/200?random=16"),
    Receta("Chupe de Camarones", "Camarones, leche, papa, huevo", "Almuerzo", "https://picsum.photos/200?random=17"),
    Receta("Tequeños", "Harina, queso, aceite", "Entrada", "https://picsum.photos/200?random=18"),
    Receta("Causa Limeña", "Papa amarilla, pollo, palta, mayonesa", "Entrada", "https://picsum.photos/200?random=19")
)

@Composable
fun RecetasScreen() {
    Scaffold() { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            /* lista de recetas */
            Text("Lista de Recetas más recientes", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                items(recetas) { receta ->
                    RecetaItem(receta)
                }
            }
        }
    }
}

@Composable
fun RecetaItem(receta: Receta) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = receta.imagen,
            contentDescription = receta.nombre,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = receta.nombre, style = MaterialTheme.typography.titleLarge)
            Text(text = "Ingredientes: ${receta.ingredientes}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Tipo: ${receta.tipo}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun RecetasScreenPreview() {
    RecetasScreen()
}

