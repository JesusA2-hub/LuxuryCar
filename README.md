# Aplicación de Venta de Carros **LuxuryCar**

---

## 📱 Descripción del Proyecto

**LuxuryCar** es una aplicación móvil Android desarrollada para la compra, venta y subasta de automóviles de lujo. La aplicación permite a los usuarios explorar autos por marca, gestionar vehículos usados, participar en subastas en vivo, guardar autos favoritos y realizar compras seguras mediante pago con tarjeta.

La interfaz está construida completamente con **Jetpack Compose**, ofreciendo una experiencia moderna, fluida e intuitiva. La información de los autos y las compras se almacena en la nube utilizando **Firebase Firestore**, lo que permite sincronización en tiempo real.

---

## 🌟 Características Principales

* 🔐 Sistema de inicio de sesión, registro y recuperación de contraseña
* 🚗 Catálogo de autos de lujo (Porsche, Ferrari, Cadillac)
* 📝 Alta, edición y eliminación de autos (CRUD completo)
* ❤️ Sistema de autos favoritos
* 🔨 Subastas en vivo de vehículos seleccionados
* 💳 Compra segura mediante formulario de pago
* ☁️ Sincronización en tiempo real con Firebase Firestore
* 🎨 Interfaz moderna con Material Design 3

---

## 🛠️ Arquitectura del Proyecto (Tecnologías Utilizadas)

* **Lenguaje:** Kotlin
* **UI:** Jetpack Compose
* **Arquitectura:** MVVM (Model – View – ViewModel)
* **Base de Datos:** Firebase Firestore
* **Gestión de Estados:** StateFlow
* **Asincronía:** Kotlin Coroutines
* **Herramientas:**

  * Android Studio
  * Gradle
  * Firebase BOM

---

## 🧱 Arquitectura MVVM

La aplicación sigue el patrón **MVVM**, separando responsabilidades para facilitar el mantenimiento y escalabilidad:

* **Model:** Clases de datos como `Car` y `Purchase`
* **View:** Pantallas construidas con Jetpack Compose (`LoginScreen`, `LuxuryCarApp`, etc.)
* **ViewModel:** `CarViewModel`, encargado de manejar la lógica de negocio y la comunicación con Firebase

---

## 📂 Estructura del Proyecto

```
.idea/
.kotlin/
app/
 └── src/
     ├── androidTest/
     ├── main/
     │   ├── java/com/example/aplicacionevaluacion/
     │   │   ├── ui/theme/
     │   │   ├── Car.kt
     │   │   ├── CarViewModel.kt
     │   │   └── MainActivity.kt
     │   └── res/
     └── test/
.gitignore
gradle/
build.gradle.kts
settings.gradle.kts
google-services.json
```

---

## 📘 Documentación Técnica

### Tutorial Completo: Creación de la Aplicación LuxuryCar

#### 1. Configuración Inicial

* Se creó un proyecto Android con **Jetpack Compose**
* Se configuró **Firebase** agregando `google-services.json`
* Se habilitó Firestore y Analytics
* Se añadieron dependencias necesarias en `build.gradle`

#### 2. Modelos de Datos

* **Car:** representa un vehículo con marca, modelo, año, precio, vendedor, estatus legal y garantía
* **Purchase:** almacena información de compras realizadas

#### 3. ViewModel

* `CarViewModel` gestiona:

  * Lectura en tiempo real de autos
  * Guardado y eliminación de autos
  * Registro de compras

Utiliza **StateFlow** para actualizar la UI automáticamente.

#### 4. Interfaz de Usuario

* Pantallas construidas con **Composable functions**
* Navegación basada en estados
* Uso de Material 3 para diseño visual

---

## ✅ Validación y Métricas

### Pruebas con Usuarios

Se realizó una fase de validación con usuarios reales para evaluar la usabilidad y satisfacción general.

| Métrica               | Resultado                               |
| --------------------- | --------------------------------------- |
| Participantes         | 10 usuarios                             |
| Perfil demográfico    | Estudiantes universitarios (18–25 años) |
| Duración de prueba    | 1 día por usuario                       |
| Calificación promedio | **4.5 / 5.0**                           |

### Resultados Detallados

* **Satisfacción General:** 90%
* **Facilidad de Uso:** 96%
* **Efectividad:** 84%
* **Diseño Visual:** 94%
* **Probabilidad de Recomendación:** 88%

### Feedback Destacado

> "La interfaz es muy intuitiva y fácil de usar." — Usuario #3

> "Las subastas hacen la app muy interesante." — Usuario #7

> "Sería genial un chat entre compradores." — Usuario #5

---

## ⚙️ Instrucciones de Instalación

### Requisitos Previos

* Android Studio Hedgehog o superior
* JDK 17+
* Android SDK 34
* Dispositivo o emulador con Android 7.0+

### Pasos de Instalación

1. Clonar el repositorio

```bash
git clone https://github.com/tu-repo/LuxuryCar.git
```

2. Abrir el proyecto en Android Studio
3. Esperar sincronización de Gradle
4. Ejecutar la aplicación

---

## 🧑‍💻 Autores

| Nombre                                | Rol                      |
| ------------------------------------- | ------------------------ |
| **Jesús Antonio Romero Duarte**       | Desarrollador Principal  |
| **Jonathan Andrés Arévalo Rodríguez** | Desarrollador UI/UX / QA |

---

© 2025 LuxuryCar App
