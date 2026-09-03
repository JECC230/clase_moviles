package com.example.tareas.model

/**
 * Representa el nivel de prioridad de un tarea o tarea.
 *
 * @property label Texto que se muestra al usuario en la interfaz.
 */
enum class Priority(val label: String) {
    LOW("Baja"),
    MEDIUM("Media"),
    HIGH("Alta")
}

/**
 * Representa un tarea o tarea dentro de la aplicación.
 *
 * @property id Identificador único del tarea (generado mediante timestamp).
 * @property title Título breve del tarea.
 * @property date Fecha del tarea (ej. "25/12/2026").
 * @property priority Nivel de prioridad asignado.
 * @property description Detalles sobre el tarea.
 * @property isCompleted Estado de completitud del tarea.
 */
data class Event(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val date: String,
    val priority: Priority,
    val description: String,
    val isCompleted: Boolean = false
)
