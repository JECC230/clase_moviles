package com.example.tareas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tareas.ui.screens.TareaFormScreen
import com.example.tareas.ui.screens.TareaListScreen
import com.example.tareas.viewmodel.TareaViewModel

/**
 * Rutas de navegación de la aplicación.
 */
object Routes {
    const val TAREA_LIST = "tarea_list"
    const val TAREA_FORM = "tarea_form"
}

/**
 * Define el grafo de navegación de la aplicación y gestiona el flujo entre pantallas.
 *
 * @param viewModel Instancia compartida del ViewModel para mantener el estado entre pantallas.
 */
@Composable
fun NavGraph(viewModel: TareaViewModel) {
    val navController: NavHostController = rememberNavController()

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
