package com.example.tareas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Niveles de prioridad disponibles para las tareas
enum class Priority(val label: String) {
    LOW("Baja"),
    MEDIUM("Media"),
    HIGH("Alta")
}

// Entidad de Room que representa la tabla "tareas" en SQLite
@Entity(tableName = "tareas")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val date: String,
    val time: String = "",
    val priority: Priority,
    val description: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.nanoTime()
)
