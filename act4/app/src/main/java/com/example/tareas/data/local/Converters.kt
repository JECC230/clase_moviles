package com.example.tareas.data.local

import androidx.room.TypeConverter
import com.example.tareas.model.Priority

// Conversores de tipos para que Room pueda almacenar enums en SQLite
class Converters {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)
}
