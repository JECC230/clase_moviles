package com.example.tareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tareas.navigation.NavGraph
import com.example.tareas.ui.theme.TareasTheme
import com.example.tareas.viewmodel.TareaViewModel

/**
 * Actividad principal de la aplicación.
 * Implementa el patrón "single-activity" utilizando Navigation Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita el diseño "edge-to-edge" para dibujar detrás de las barras del sistema
        enableEdgeToEdge()

        setContent {
            // Aplica el tema de Material 3 a toda la aplicación
            TareasTheme {
                // Instancia compartida del ViewModel vinculada al ciclo de vida de la Activity
                val tareaViewModel: TareaViewModel = viewModel()

                NavGraph(viewModel = tareaViewModel)
            }
        }
    }
}
