package com.example.tareas.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tareas.ui.components.TareaCard
import com.example.tareas.viewmodel.TareaViewModel

/**
 * Pantalla principal de la aplicación.
 * Muestra la lista de todos los tareas registrados e incluye un botón flotante para crear nuevos.
 *
 * @param viewModel ViewModel que expone el estado de los tareas y las acciones.
 * @param onAddTareaClick Callback para navegar a la pantalla de creación de tareas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaListScreen(
    viewModel: TareaViewModel,
    onAddTareaClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Tareas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTareaClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar nuevo tarea")
            }
        }
    ) { innerPadding ->

        val events = viewModel.events

        if (events.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aún no tienes tareas.\nToca el botón + para crear el primero.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                items(events, key = { it.id }) { event ->
                    TareaCard(
                        event = event,
                        onToggleCompleted = { viewModel.toggleCompleted(it) },
                        onDelete = { viewModel.removeTarea(it) },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}
