package com.example.tareas.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun TimePickerDialog(
    title: String = "Selecciona una hora",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(shape = MaterialTheme.shapes.extraLarge) {
            Box(modifier = Modifier.padding(24.dp)) {
                Column {
                    Text(text = title, style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.height(12.dp))

                    content()

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(onClick = onDismiss) { Text("Cancelar") }
                        TextButton(onClick = onConfirm) { Text("Aceptar") }
                    }
                }
            }
        }
    }
}
