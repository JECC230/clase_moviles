package com.example.tareas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tareas.ui.screens.TareaFormScreen
import com.example.tareas.ui.screens.TareaListScreen
import com.example.tareas.viewmodel.TareaViewModel

// Definición de rutas de navegación de la app
object Routes {
    const val TAREA_LIST = "tarea_list"
    const val TAREA_FORM = "tarea_form"
}

@Composable
fun NavGraph(viewModel: TareaViewModel) {
    val navController: NavHostController = rememberNavController()

    // Configuración del NavHost para gestionar la transición entre pantallas
    NavHost(
        navController = navController,
        startDestination = Routes.TAREA_LIST
    ) {
        composable(Routes.TAREA_LIST) {
            TareaListScreen(
                viewModel = viewModel,
                onAddTareaClick = {
                    navController.navigate(Routes.TAREA_FORM)
                }
            )
        }
        composable(Routes.TAREA_FORM) {
            TareaFormScreen(
                viewModel = viewModel,
                onTareaSaved = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
