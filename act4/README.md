# Tareas 📅

Este proyecto está pensado como material didáctico: el código está comentado paso a paso
en los puntos clave (`remember`, `mutableStateOf`, `animate*`, `LaunchedEffect`/estado,
`NavHost`, etc.) para que puedas entender el **por qué**, no solo el **qué**.

---

## 1. Estructura del proyecto

```
app/src/main/java/com/example/tareas/
├── MainActivity.kt          -> Punto de entrada de la app
├── model/
│   └── Tarea.kt              -> Modelo de datos y entidad de Room: Event y Priority
├── data/
│   └── local/
│       ├── TareaDao.kt        -> Operaciones CRUD sobre la tabla "tareas" (Room)
│       ├── AppDatabase.kt     -> Instancia Singleton de la base de datos SQLite
│       └── Converters.kt      -> TypeConverter para guardar el enum Priority
├── viewmodel/
│   └── TareaViewModel.kt      -> Estado (StateFlow) y lógica: agregar, alternar, eliminar
├── navigation/
│   └── NavGraph.kt            -> NavHost + rutas entre pantallas
└── ui/
    ├── theme/                 -> Colores, tipografía y tema (claro/oscuro)
    ├── components/
    │   └── TareaCard.kt        -> Tarjeta expandible: swipe-to-dismiss, zoom y animaciones
    └── screens/
        ├── TareaListScreen.kt   -> Lista/cuadrícula responsive de tareas + FAB
        └── TareaFormScreen.kt   -> Formulario con validaciones
```

**Flujo de datos (por qué se organizó así):**
`TareaViewModel` es la única "fuente de la verdad" de los datos, y a su vez delega en
`TareaDao` para leer/escribir en la base de datos Room. Se crea una sola vez en
`MainActivity` y se comparte entre `TareaListScreen` y `TareaFormScreen` a través de
`NavGraph`. Así, cuando el formulario agrega una tarea, la lista lo ve inmediatamente
al volver atrás — no hay que pasar datos "de ida y vuelta" entre pantallas. Como los datos
viven en SQLite (no solo en memoria), las tareas **sobreviven a que se cierre la app**.

---

#
## 3. Checklist de criterios cumplidos ✅

Usa esta lista para verificar tu propia entrega o la de un compañero (revisión por pares):

- [ ] **Modelo de datos** (`Tarea.kt`): campos `id`, `title`, `date`, `priority`, `description`,
      `isCompleted`, `createdAt`, anotado como entidad de Room (`@Entity`).
- [ ] **Persistencia con Room** (`data/local/`):
  - [ ] `TareaDao` expone `getAll(): Flow<List<Event>>`, `insert`, `update` y `delete` (suspend)
  - [ ] `AppDatabase` es una instancia Singleton thread-safe
  - [ ] Las tareas sobreviven a cerrar y reabrir la app
- [ ] **ViewModel** (`TareaViewModel.kt`, `AndroidViewModel`) expone `events: StateFlow<List<Event>>` y:
  - [ ] `addTarea(...)` para agregar
  - [ ] `toggleCompleted(...)` para alternar el estado completado/pendiente
  - [ ] `removeTarea(...)` para eliminar
- [ ] **Tema** (`ui/theme/`): esquema de colores Material 3 para **claro y oscuro**
      (`TareasTheme`), con tipografía consistente (`Type.kt`).
- [ ] **TareaCard**:
  - [ ] Se expande/contrae con animación (`AnimatedVisibility` + `expandVertically`/`shrinkVertically`)
  - [ ] Aparece con animación al agregarse (`AnimatedVisibility` con `fadeIn` + `slideInVertically`)
  - [ ] El color de la tarjeta cambia suavemente al completarse (`animateColorAsState`)
  - [ ] Ícono de flecha que indica el estado expandido/contraído
  - [ ] Indicador visual de prioridad (círculo de color: verde/naranja/rojo)
  - [ ] Se puede eliminar deslizando (`SwipeToDismissBox`) **y** con un botón visible
  - [ ] El detalle expandido soporta pinch-to-zoom (`detectTransformGestures` + `graphicsLayer`)
- [ ] **TareaListScreen**:
  - [ ] `LazyColumn` (pantallas compactas) o `LazyVerticalGrid` de 2 columnas (pantallas
        medianas/grandes), decidido con `BoxWithConstraints`
  - [ ] Animaciones de aparición/desaparición de la lista (`Modifier.animateItem()`)
  - [ ] `FloatingActionButton` que navega al formulario
  - [ ] Mensaje de estado vacío cuando no hay tareas
- [ ] **TareaFormScreen**:
  - [ ] Campos: título, fecha, prioridad (selector) y descripción
  - [ ] Validación en tiempo real: título no vacío, fecha obligatoria, descripción con
        longitud mínima (10 caracteres)
  - [ ] Mensajes de error en rojo bajo cada campo inválido
  - [ ] Botón "Guardar" deshabilitado hasta que el formulario sea válido
- [ ] **Navegación** (`NavGraph.kt`): `NavHost` + `NavController` entre lista y formulario,
      compartiendo el mismo `TareaViewModel`.
- [ ] El código compila y corre sin errores en un emulador o dispositivo físico.

