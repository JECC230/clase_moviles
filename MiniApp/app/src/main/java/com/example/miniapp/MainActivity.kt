package com.example.miniapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================================
// MODELO DE DATOS
// ============================================================
data class Task(
    val id: Long = System.currentTimeMillis() + (0..1000).random(),
    val title: String,
    val isCompleted: Boolean = false,
)

// ============================================================
// PERSONALIZACIÓN DEL TEMA (Material Theme Claro / Oscuro)
// ============================================================
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF80CBC4),
    onPrimary = Color(0xFF003731),
    primaryContainer = Color(0xFF004D40),
    secondary = Color(0xFFFFB74D),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFECEFF1)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00695C),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2DFDB),
    secondary = Color(0xFFF57C00),
    background = Color(0xFFF7F9FA),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF263238)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}

// ============================================================
// ACTIVITY PRINCIPAL
// ============================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskAppScreen()
                }
            }
        }
    }
}

// ============================================================
// PANTALLA PRINCIPAL (Manejo de Estado Central)
// Contexto: Miniapp para registrar y marcar tareas completadas
// ============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAppScreen() {
    val tasks = remember {
        mutableStateListOf(
            Task(id = 1, title = "Revisar layouts en Compose", isCompleted = true),
            Task(id = 2, title = "Implementar manejo de estado", isCompleted = false)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis Tareas",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Composable 1: Entrada de datos
            TaskInput(
                onAddTask = { newTitle ->
                    if (newTitle.isNotBlank()) {
                        tasks.add(Task(title = newTitle.trim()))
                    }
                }
            )

            HorizontalDivider()

            // Layout adaptativo: LazyColumn optimizada
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay tareas pendientes.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = tasks, key = { it.id }) { task ->
                        // Composable 2: Renderizado individual de item
                        TaskItem(
                            task = task,
                            onToggleComplete = {
                                val index = tasks.indexOfFirst { it.id == task.id }
                                if (index != -1) {
                                    tasks[index] = task.copy(isCompleted = !task.isCompleted)
                                }
                            },
                            onDelete = {
                                tasks.remove(task)
                            }
                        )
                    }
                }
            }
        }
    }
}

// ============================================================
// COMPOSABLE 1: TaskInput
// ============================================================
@Composable
fun TaskInput(
    onAddTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var textState by remember { mutableStateOf("") }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Nueva tarea") },
            singleLine = true,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp)
        )

        Button(
            onClick = {
                if (textState.isNotBlank()) {
                    onAddTask(textState)
                    textState = ""
                }
            },
            modifier = Modifier.height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar")
        }
    }
}

// ============================================================
// COMPOSABLE 2: TaskItem
// ============================================================
@Composable
fun TaskItem(
    task: Task,
    onToggleComplete: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggleComplete() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = task.title,
                fontSize = 16.sp,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                color = if (task.isCompleted) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

// ============================================================
// PREVIEWS
// ============================================================
@Preview(showBackground = true, name = "Modo Claro")
@Composable
fun TaskAppPreviewLight() {
    AppTheme(darkTheme = false) {
        TaskAppScreen()
    }
}

@Preview(showBackground = true, name = "Modo Oscuro")
@Composable
fun TaskAppPreviewDark() {
    AppTheme(darkTheme = true) {
        TaskAppScreen()
    }
}