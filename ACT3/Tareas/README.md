# Tareas 📅

App de ejemplo para la clase de **Jetpack Compose**: un registro simple de eventos/recordatorios
(crear, ver, marcar como completado y eliminar) hecho 100% con Jetpack Compose y Material 3.

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

**Flujo de datos (por qué se organizó así):**
`EventViewModel` es la única "fuente de la verdad" de los datos. Se crea una sola vez en
`MainActivity` y se comparte entre `EventListScreen` y `EventFormScreen` a través de
`NavGraph`. Así, cuando el formulario agrega un evento, la lista lo ve inmediatamente
al volver atrás — no hay que pasar datos "de ida y vuelta" entre pantallas.

---

## 2. Cómo ejecutar la app

### Requisitos
- **Android Studio** Ladybug (2024.2) o más reciente (soporta Kotlin 2.0 y AGP 8.6).
- JDK 17 (Android Studio suele traer uno embebido; no necesitas instalar nada aparte).
- Un emulador o celular Android físico con **Android 7.0 (API 24)** o superior, con
  depuración USB activada si usas un dispositivo físico.

### Pasos
1. Abre Android Studio → **File > Open** → selecciona la carpeta del proyecto.
2. Espera a que termine el **Gradle Sync** (barra de progreso abajo). La primera vez puede
   tardar varios minutos porque descarga las dependencias.
   - Si Android Studio pregunta *"Gradle wrapper not found, create one?"*, acepta que lo cree
     automáticamente (esto es normal en un proyecto nuevo).
3. Selecciona un emulador o conecta tu celular (arriba, junto al botón ▶️ Run).
4. Presiona **Run ▶️** (o `Shift + F10`).
5. La app abre directamente en la lista de recordatorios (vacía la primera vez). Toca el
   botón **+** para crear el primero.

### Errores comunes al abrir por primera vez
| Problema | Solución |
|---|---|
| "SDK location not found" | Android Studio crea `local.properties` automáticamente al sincronizar; si no, ve a **File > Project Structure > SDK Location**. |
| Versión de Gradle/AGP distinta | Deja que Android Studio te ofrezca actualizar (**Upgrade Assistant**); las versiones de este proyecto son estables al momento de crearlo. |
| Falta el SDK de alguna API | Ve a **Tools > SDK Manager** e instala la API 34. |

---

## 3. Checklist de criterios cumplidos ✅

Usa esta lista para verificar tu propia entrega o la de un compañero (revisión por pares):

- [ ] **Modelo de datos** (`Event.kt`): campos `id`, `title`, `date`, `priority`, `description`.
- [ ] **ViewModel** (`EventViewModel.kt`) usa `mutableStateListOf<Event>()` y expone:
  - [ ] `addEvent(...)` para agregar
  - [ ] `toggleCompleted(...)` para alternar el estado completado/pendiente
  - [ ] `removeEvent(...)` para eliminar
- [ ] **Tema** (`ui/theme/`): esquema de colores Material 3 para **claro y oscuro**
      (`TareasTheme`), con tipografía consistente (`Type.kt`).
- [ ] **EventCard**:
  - [ ] Se expande/contrae con animación (`Modifier.animateContentSize()`)
  - [ ] Ícono de flecha que indica el estado expandido/contraído
  - [ ] Indicador visual de prioridad (círculo de color: verde/naranja/rojo)
  - [ ] Se puede eliminar deslizando (`SwipeToDismissBox`) **y** con un botón visible
- [ ] **EventListScreen**:
  - [ ] `LazyColumn` con animaciones de lista (`Modifier.animateItem()`)
  - [ ] `FloatingActionButton` que navega al formulario
  - [ ] Mensaje de estado vacío cuando no hay eventos
- [ ] **EventFormScreen**:
  - [ ] Campos: título, fecha, prioridad (selector) y descripción
  - [ ] Validación en tiempo real: título no vacío, fecha obligatoria, descripción con
        longitud mínima (10 caracteres)
  - [ ] Mensajes de error en rojo bajo cada campo inválido
  - [ ] Botón "Guardar" deshabilitado hasta que el formulario sea válido
- [ ] **Navegación** (`NavGraph.kt`): `NavHost` + `NavController` entre lista y formulario,
      compartiendo el mismo `EventViewModel`.
- [ ] El código compila y corre sin errores en un emulador o dispositivo físico.

---

## 4. Accesibilidad: probando con TalkBack 🔊

TalkBack es el lector de pantalla de Android. Probar tu app con TalkBack activado es un
buen hábito para detectar problemas de accesibilidad antes de que un usuario real los sufra.

### Cómo activarlo
1. En el emulador o celular: **Ajustes > Accesibilidad > TalkBack** → actívalo.
   - Atajo rápido: mantén presionados ambos botones de volumen 3 segundos (si está habilitado
     el atajo de accesibilidad).
2. Con TalkBack activo, la navegación cambia: un toque simple **selecciona** y **lee en voz
   alta** el elemento; doble toque **lo activa** (equivale al clic normal).

### Qué revisar en esta app
- **EventCard**: al deslizar el dedo sobre la tarjeta, TalkBack debería anunciar el título,
  la fecha y la prioridad del evento (gracias a que el texto normal ya es leído automáticamente).
- **Botones con solo ícono** (marcar completado, eliminar, flecha de expandir): verifica que
  cada `IconButton` tenga un `contentDescription` que tenga sentido leído en voz alta
  (ya están definidos en el código, ej: `"Eliminar evento"`, `"Marcar como completado"`).
- **Swipe to dismiss**: el gesto de deslizar puede ser difícil de descubrir/ejecutar con
  TalkBack activo; por eso esta app SIEMPRE ofrece también el botón de eliminar como
  alternativa accesible.
- **Formulario**: cada `OutlinedTextField` debe anunciar su `label` (ej. "Título") y, si hay
  error, el mensaje de `supportingText` en rojo también debería ser leído.
- **Contraste de color**: la prioridad se indica con color, pero también con texto
  ("Prioridad: Alta/Media/Baja"), para no depender solo del color (importante para personas
  con daltonismo).

### Ejercicio sugerido para la clase
Desactiva el sonido de tu celular, activa TalkBack, y trata de crear un recordatorio nuevo
completo usando solo gestos y el lector de pantalla (sin mirar). Anota en tu reporte qué
partes fueron difíciles de usar así.

---

## 5. Plantilla de reporte de pruebas y entrega 📋

Copia esta plantilla en un documento aparte (o al final de este mismo README, en una
sección nueva) y complétala antes de entregar tu proyecto.

```markdown
# Reporte de pruebas — Tareas

**Nombre del estudiante:**
**Fecha de entrega:**
**Dispositivo/emulador usado para probar:** (ej: Pixel 8 API 34, o celular físico modelo X)

## 1. Funcionalidades probadas

| Funcionalidad                                   | ¿Funciona? (Sí/No) | Observaciones |
|--------------------------------------------------|---------------------|---------------|
| Crear un evento con datos válidos                |                     |               |
| Botón "Guardar" bloqueado con formulario inválido|                     |               |
| Mensajes de error se muestran correctamente      |                     |               |
| Expandir/contraer una tarjeta (animación)        |                     |               |
| Marcar un evento como completado / pendiente     |                     |               |
| Eliminar un evento deslizando (swipe)            |                     |               |
| Eliminar un evento con el botón de basura        |                     |               |
| Navegar de la lista al formulario y volver       |                     |               |
| La lista conserva los datos al rotar la pantalla |                     |               |
| Tema oscuro se ve correctamente                  |                     |               |

## 2. Prueba de accesibilidad (TalkBack)

- ¿Se pudo crear un recordatorio completo usando solo TalkBack? (Sí/No):
- ¿Todos los botones con ícono anuncian una descripción clara? (Sí/No):
- Dificultades encontradas:

## 3. Capturas de pantalla
(Adjunta capturas de: lista vacía, lista con eventos, tarjeta expandida,
formulario con errores, formulario válido, tema oscuro)

## 4. Errores encontrados y cómo se resolvieron
(Si tuviste que corregir algo del código base, descríbelo aquí)

## 5. Conclusiones
(¿Qué aprendiste sobre Compose, estado, animaciones o navegación con este proyecto?)
```

---

## 6. Ideas para extender el proyecto (opcional)

Si quieres ir más allá de los requisitos de la clase:
- Reemplazar el campo de texto de fecha por un `DatePickerDialog` de Material 3.
- Guardar los eventos en una base de datos local con **Room** para que no se pierdan
  al cerrar la app (actualmente se pierden porque solo viven en memoria mientras corre la app).
- Agregar notificaciones locales que recuerden el evento en su fecha.
- Agregar una pantalla de edición (reutilizando `EventFormScreen` con un evento existente).

---

**Recuerda:** este proyecto prioriza la claridad sobre la "perfección arquitectónica".
No hay repositorios, casos de uso, ni inyección de dependencias complejas a propósito —
eso lo verás en cursos más avanzados. Aquí el objetivo es que entiendas bien los
fundamentos de estado, recomposición, animaciones y navegación en Compose.
