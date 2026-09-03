// Build script del módulo "app". Aquí configuramos el SDK, el lenguaje y las dependencias
// (librerías externas) que la app va a usar.

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Este plugin habilita el "compilador de Compose" para Kotlin 2.0+.
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    // El namespace es el paquete base de la app (debe coincidir con las carpetas de /java)
    namespace = "com.example.tareas"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tareas"
        minSdk = 24        // Android 7.0 en adelante
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        // Habilita Jetpack Compose en este módulo
        compose = true
    }
}

dependencies {
    // --- Núcleo de Android + integración de Compose con Activity/ViewModel ---
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // --- BOM (Bill of Materials) de Compose: fija versiones compatibles entre sí,
    // así no tenemos que escribir el número de versión en cada librería de Compose. ---
    implementation(platform("androidx.compose:compose-bom:2024.09.03"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // --- Material Icons Extended: necesario para íconos como CalendarMonth, Flag,
    // RadioButtonUnchecked, etc. (los íconos "básicos" no incluyen estos). ---
    implementation("androidx.compose.material:material-icons-extended")

    // --- Navigation Compose: nos permite movernos entre EventListScreen y EventFormScreen ---
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Herramientas de depuración/preview de Compose (solo en builds de debug)
    debugImplementation("androidx.compose.ui:ui-tooling")
}
