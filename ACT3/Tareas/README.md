# Tareas 📅


Este proyecto está pensado como material didáctico: el código está comentado paso a paso
en los puntos clave (`remember`, `mutableStateOf`, `animate*`, `LaunchedEffect`/estado,
`NavHost`, etc.) para que puedas entender el **por qué**, no solo el **qué**.

---

## 1. Estructura del proyecto

```
app/src/main/java/com/example/eventreminderapp/
├── MainActivity.kt          -> Punto de entrada de la app
├── model/
│   └── Event.kt              -> Modelo de datos: Event y Priority
├── viewmodel/
│   └── EventViewModel.kt      -> Estado y lógica: agregar, alternar, eliminar eventos
├── navigation/
│   └── NavGraph.kt            -> NavHost + rutas entre pantallas
└── ui/
    ├── theme/                 -> Colores, tipografía y tema (claro/oscuro)
    ├── components/
    │   └── EventCard.kt        -> Tarjeta expandible con swipe-to-dismiss
    └── screens/
        ├── EventListScreen.kt   -> Lista de eventos + FAB
        └── EventFormScreen.kt   -> Formulario con validaciones
```
