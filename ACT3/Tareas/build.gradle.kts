// Build script a nivel RAÍZ del proyecto.
// Aquí solo declaramos los plugins (con sus versiones) que los módulos podrán aplicar.
// "apply false" significa: "descarga este plugin, pero no lo apliques todavía aquí,
// se aplicará dentro de app/build.gradle.kts".

plugins {
    id("com.android.application") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}
