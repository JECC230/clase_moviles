package com.example.tareas.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tareas.model.Event
import com.example.tareas.model.Priority
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Pruebas del [TareaDao] contra una base de datos Room *en memoria*.
 *
 * Estas son pruebas instrumentadas (corren en un dispositivo/emulador, no en la JVM local)
 * porque Room necesita un motor SQLite real, que solo está disponible en Android.
 * Reemplazan al viejo `TareaViewModelTest`: ahora que el ViewModel es un delgado
 * envoltorio alrededor del DAO (ver `TareaViewModel.kt`), tiene más sentido probar la
 * lógica de persistencia directamente aquí.
 */
@RunWith(AndroidJUnit4::class)
class TareaDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: TareaDao

    @Before
    fun crearBaseDeDatosEnMemoria() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Permite ejecutar operaciones en el hilo principal SOLO en pruebas; en la app
            // real Room sigue prohibiéndolo y todo pasa por `viewModelScope.launch`.
            .allowMainThreadQueries()
            .build()
        dao = database.tareaDao()
    }

    @After
    fun cerrarBaseDeDatos() {
        database.close()
    }

    @Test
    fun laListaEmpiezaVacia() = runTest {
        assertTrue(dao.getAll().first().isEmpty())
    }

    @Test
    fun insertarAgregaLaTareaCorrectamente() = runTest {
        dao.insert(
            Event(
                title = "Comprar leche",
                date = "10/09/2026",
                priority = Priority.MEDIUM,
                description = "Ir al supermercado"
            )
        )

        val tareas = dao.getAll().first()
        assertEquals(1, tareas.size)
        assertEquals("Comprar leche", tareas[0].title)
        assertEquals(Priority.MEDIUM, tareas[0].priority)
        assertEquals(false, tareas[0].isCompleted)
    }

    @Test
    fun eliminarQuitaLaTareaDeLaLista() = runTest {
        dao.insert(Event(title = "Tarea 1", date = "10/09/2026", priority = Priority.LOW, description = "Desc 1"))
        dao.insert(Event(title = "Tarea 2", date = "11/09/2026", priority = Priority.HIGH, description = "Desc 2"))

        val insertadas = dao.getAll().first()
        assertEquals(2, insertadas.size)

        dao.delete(insertadas.first { it.title == "Tarea 1" })

        val restantes = dao.getAll().first()
        assertEquals(1, restantes.size)
        assertEquals("Tarea 2", restantes[0].title)
    }

    @Test
    fun actualizarCambiaElEstadoDeCompletado() = runTest {
        dao.insert(
            Event(
                title = "Estudiar Kotlin",
                date = "12/09/2026",
                priority = Priority.HIGH,
                description = "Capítulo de Compose"
            )
        )

        val tarea = dao.getAll().first().first()
        assertEquals(false, tarea.isCompleted)

        dao.update(tarea.copy(isCompleted = true))

        val actualizada = dao.getAll().first().first()
        assertTrue(actualizada.isCompleted)
    }
}
