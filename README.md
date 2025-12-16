# 📱 Aplicación de Notas con MongoDB Realm (Atlas Device SDK)

## 🎯 Descripción del Proyecto

Esta es una **aplicación Android moderna** que permite a los usuarios **crear, leer, actualizar y eliminar notas (CRUD)** utilizando **MongoDB Realm** como base de datos local embebida. La aplicación está construida con **Jetpack Compose** para la interfaz de usuario y sigue el patrón de arquitectura **MVVM (Model–View–ViewModel)**.

---

## 🌟 Características Principales

* ✅ Crear notas con título y contenido
* ✅ Editar notas existentes en tiempo real
* ✅ Eliminar notas con confirmación visual
* ✅ Base de datos local ultra-rápida (**MongoDB Realm**)
* ✅ Actualizaciones reactivas automáticas (**Flow**)
* ✅ Interfaz moderna con **Material Design 3**
* ✅ Funciona **100% offline** (no requiere internet)
* ✅ Arquitectura limpia y escalable (**MVVM**)
* ✅ Rendimiento extremo (operaciones ~1–5 ms)

---

## 🏗️ Arquitectura del Proyecto (MVVM)

```
VIEW (UI - Jetpack Compose)
   │ collectAsState()
   ▼
VIEWMODEL (StateFlow + lógica)
   │ suspend functions
   ▼
REPOSITORY (CRUD + Flow)
   │ realm.write{}, realm.query<T>()
   ▼
MODEL (RealmObject)
   ▼
MongoDB Realm (Base de datos local)
```

### Capas

* **View**: `MainActivity.kt`, pantallas Compose
* **ViewModel**: `NotesViewModel.kt`, estados y lógica
* **Repository**: `NoteRepository.kt`, acceso a datos
* **Model**: `Note.kt`, entidad RealmObject

---

## 🎯 ¿Por qué MVVM + MongoDB Realm?

* Separación clara de responsabilidades
* Arquitectura testeable y mantenible
* Actualizaciones automáticas en UI
* Offline-first real
* Rendimiento muy superior a soluciones cloud

---

## 🆚 Firebase Firestore vs MongoDB Realm

| Característica | Firebase Firestore | MongoDB Realm  |
| -------------- | ------------------ | -------------- |
| Tipo de BD     | Cloud              | Local embebida |
| Internet       | Requerido*         | No requerido   |
| Velocidad      | 100–500 ms         | 1–5 ms         |
| Costo          | Pago por uso       | Gratis (local) |
| Queries        | Limitados          | Avanzados      |
| Encriptación   | No nativa          | AES-256        |
| Transacciones  | Limitadas          | ACID completas |

*Firestore offline usa caché limitada

---

## 📋 Requisitos Previos

### Software

* Android Studio **Koala / Ladybug (2024+)**
* JDK 11 o superior (incluido en Android Studio)

### Conocimientos

* Kotlin básico
* Jetpack Compose básico
* Coroutines
* Conceptos CRUD

---

## 📁 Estructura del Proyecto

```
MongoDBExample/
├── app/
│   ├── src/main/java/mx/edu/utng/arg/mongodbexample/
│   │   ├── data/
│   │   │   ├── Note.kt
│   │   │   └── NoteRepository.kt
│   │   ├── ui/
│   │   │   └── NotesViewModel.kt
│   │   ├── MainActivity.kt
│   │   └── App.kt
│   └── res/
├── gradle/libs.versions.toml
├── build.gradle.kts
└── README.md
```

---

## 🛠️ Instalación y Configuración

### 1️⃣ Crear el proyecto

* New Project → Empty Activity (Compose)
* Min SDK: 24
* Lenguaje: Kotlin

### 2️⃣ Catálogo de versiones (`libs.versions.toml`)

Incluye versiones de:

* Kotlin
* Compose
* MongoDB Realm
* Lifecycle
* Coroutines

> No se requiere `google-services.json`

---

## ⚙️ Configuración Gradle

### Plugins principales

```kotlin
alias(libs.plugins.android.application)
alias(libs.plugins.kotlin.android)
alias(libs.plugins.realm.kotlin)
```

### Dependencia clave

```kotlin
implementation(libs.realm.kotlin.library.base)
```

---

## 💻 Implementación Principal

### 📦 Modelo (`Note.kt`)

* Hereda de `RealmObject`
* Usa `@PrimaryKey ObjectId`
* Persistencia automática

### 💾 Repository (`NoteRepository.kt`)

* CRUD completo
* Queries reactivas con `Flow`
* Abstracción de la base de datos

### 🧠 ViewModel (`NotesViewModel.kt`)

* Manejo de estados (`StateFlow`)
* Validaciones
* Coordinación con Repository

### 🚀 Inicialización (`App.kt`)

* Configuración global de Realm
* Apertura de la base de datos local

---

## 🎨 Interfaz de Usuario

* Jetpack Compose
* Material Design 3
* LazyColumn para listado
* Edición en tiempo real

---

## ▶️ Ejecución

* Emulador recomendado: Pixel 6 (API 34)
* O dispositivo físico con depuración USB

---

## 🧪 Pruebas

* CRUD completo
* Persistencia tras cerrar la app
* Funcionamiento en modo avión
* Medición de rendimiento (~2 ms por inserción)

---

## 🐛 Problemas Comunes

* ❌ Realm no inicializa → Falta `android:name=".App"`
* ❌ Error write transaction → Usar `realm.write{}`
* ❌ Gradle sync → Clean + Rebuild

---

## 🚀 Características Avanzadas

* Encriptación AES-256
* Queries complejos
* Relaciones y objetos embebidos
* Migraciones de esquema
* Device Sync opcional (Atlas)

---

## 🎓 Ejercicios Propuestos

1. Agregar timestamps
2. Sistema de categorías
3. Búsqueda en tiempo real
4. Estadísticas de notas

---

## 📚 Recursos

* MongoDB Realm Kotlin SDK
* Jetpack Compose
* Kotlin Coroutines
* MVVM Architecture
* Realm Studio

---

## 👨‍💻 Autor

**Profesor:** Tacho
**Institución:** Universidad Tecnológica del Norte de Guanajuato (UTNG)
**Materia:** Desarrollo de Aplicaciones Móviles

---

## 📄 Licencia

Proyecto educativo de libre uso con fines académicos.

---

🚀 **¡Feliz codificación con MongoDB Realm!**
