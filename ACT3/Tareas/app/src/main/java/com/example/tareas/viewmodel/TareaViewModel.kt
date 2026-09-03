package com.example.tareas.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tareas.model.Event
import com.example.tareas.model.Priority

/**
 * ViewModel encargado de gestionar el estado y la lógica de los tareas.
 * Mantiene la lista de tareas en memoria y expone funciones para modificarla,
 * sobreviviendo a los cambios de configuración.
 */
class TareaViewModel : ViewModel() {

    private val _tareas = mutableStateListOf<Event>()

    /**
     * Lista inmutable de tareas expuesta a la UI.
     */
    val events: List<Event>
        get() = _tareas

    /**
     * Crea y añade un nuevo tarea a la lista.
     *
     * @param title Título del tarea.
     * @param date Fecha del tarea.
     * @param priority Nivel de prioridad.
     * @param description Descripción detallada.
     */
    fun addTarea(title: String, date: String, priority: Priority, description: String) {
        _tareas.add(
            Event(
                title = title,
                date = date,
                priority = priority,
                description = description
            )
        )
    }

    /**
     * Elimina el tarea especificado de la lista.
     *
     * @param event Tarea a eliminar.
     */
    fun removeTarea(event: Event) {
        _tareas.removeIf { it.id == event.id }
    }

    /**
     * Cambia el estado del tarea especificado, alternando entre completado y pendiente.
     *
     * @param event Tarea a actualizar.
     */
    fun toggleCompleted(event: Event) {
        val index = _tareas.indexOfFirst { it.id == event.id }
        if (index != -1) {
            val tareaActual = _tareas[index]
            _tareas[index] = tareaActual.copy(isCompleted = !tareaActual.isCompleted)
        }
    }
}
