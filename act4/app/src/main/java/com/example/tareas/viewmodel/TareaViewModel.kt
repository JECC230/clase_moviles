package com.example.tareas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tareas.data.local.AppDatabase
import com.example.tareas.data.local.TareaDao
import com.example.tareas.model.Event
import com.example.tareas.model.Priority
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareaViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: TareaDao = AppDatabase.getDatabase(application).tareaDao()

    // Expone la lista como StateFlow reactivo con caché de 5s ante cambios de configuración
    val events: StateFlow<List<Event>> = dao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun addTarea(title: String, date: String, time: String, priority: Priority, description: String) {
        viewModelScope.launch {
            dao.insert(
                Event(
                    title = title,
                    date = date,
                    time = time,
                    priority = priority,
                    description = description
                )
            )
        }
    }

    fun removeTarea(event: Event) {
        viewModelScope.launch {
            dao.delete(event)
        }
    }

    fun toggleCompleted(event: Event) {
        viewModelScope.launch {
            dao.update(event.copy(isCompleted = !event.isCompleted))
        }
    }
}
