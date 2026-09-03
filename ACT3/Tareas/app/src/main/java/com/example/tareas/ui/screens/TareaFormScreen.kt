package com.example.tareas.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tareas.model.Priority
import com.example.tareas.viewmodel.TareaViewModel

/**
 * Pantalla de formulario para crear un nuevo tarea.
 * Incluye validaciones en tiempo real para todos los campos.
 *
 * @param viewModel ViewModel donde se guardará el nuevo tarea.
 * @param onTareaSaved Callback ejecutado después de guardar exitosamente.
 * @param onBackClick Callback ejecutado al presionar el botón de volver.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaFormScreen(
    viewModel: TareaViewModel,
    onTareaSaved: () -> Unit,
    onBackClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var priorityMenuExpanded by remember { mutableStateOf(false) }

    val tituloVacio = title.isBlank()
    val fechaVacia = date.isBlank()
    val descripcionVacia = description.isBlank()
    val descripcionMuyCorta = description.isNotBlank() && description.trim().length < 10

    val formularioValido = !tituloVacio && !fechaVacia && !descripcionVacia && !descripcionMuyCorta

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Tarea") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                isError = tituloVacio,
                supportingText = {
                    if (tituloVacio) {
                        Text("El título es obligatorio", color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // TODO: En una implementación real se recomienda usar un DatePicker en lugar de un campo de texto.
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Fecha (dd/mm/aaaa)") },
                leadingIcon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null) },
                isError = fechaVacia,
                supportingText = {
                    if (fechaVacia) {
                        Text("La fecha es obligatoria", color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Prioridad", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            ExposedDropdownMenuBox(
                expanded = priorityMenuExpanded,
                onExpandedChange = { priorityMenuExpanded = it }
            ) {
                OutlinedTextField(
                    value = priority.label,
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Filled.Flag, contentDescription = null) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = priorityMenuExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = priorityMenuExpanded,
                    onDismissRequest = { priorityMenuExpanded = false }
                ) {
                    Priority.entries.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion.label) },
                            onClick = {
                                priority = opcion
                                priorityMenuExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                isError = descripcionVacia || descripcionMuyCorta,
                supportingText = {
                    when {
                        descripcionVacia -> Text(
                            "La descripción es obligatoria",
                            color = MaterialTheme.colorScheme.error
                        )
                        descripcionMuyCorta -> Text(
                            "La descripción debe tener al menos 10 caracteres",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                minLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.addTarea(
                        title = title.trim(),
                        date = date.trim(),
                        priority = priority,
                        description = description.trim()
                    )
                    onTareaSaved()
                },
                enabled = formularioValido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar tarea")
            }
        }
    }
}
