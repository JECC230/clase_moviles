// Este archivo le dice a Gradle qué repositorios usar y qué módulos forman parte del proyecto.
// Para este proyecto de aprendizaje solo tenemos un módulo: "app".

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Tareas"
include(":app")
