package com.example.tareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tareas.navigation.NavGraph
import com.example.tareas.ui.theme.TareasTheme
import com.example.tareas.viewmodel.TareaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TareasTheme {
                // Instancia del ViewModel compartido y configuración del grafo de navegación
                val tareaViewModel: TareaViewModel = viewModel()
                NavGraph(viewModel = tareaViewModel)
            }
        }
    }
}
