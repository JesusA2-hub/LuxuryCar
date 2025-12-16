# 🚗 LuxuryCar - Aplicación de Venta de Carros de Lujo

## 📌 Descripción del Proyecto

**LuxuryCar** es una aplicación móvil Android desarrollada en **Kotlin** utilizando **Jetpack Compose** que permite a los usuarios:

- Comprar autos de lujo
- Vender autos
- Participar en subastas en tiempo real

La aplicación implementa una arquitectura **MVVM (Model–View–ViewModel)** y utiliza **Firebase Firestore** como base de datos NoSQL en tiempo real. La interfaz está basada en **Material Design 3**, ofreciendo una experiencia visual elegante, fluida y profesional.

---

## 🏗️ Arquitectura del Proyecto – MVVM

La aplicación sigue el patrón **MVVM**, separando responsabilidades:

- **Model**: Clases de datos (`Car`, `Purchase`)
- **ViewModel**: Lógica de negocio y conexión con Firebase
- **View**: Interfaz de usuario con Jetpack Compose

**Ventajas:**
- ✅ Código limpio y organizado
- ✅ Fácil mantenimiento
- ✅ Alta escalabilidad
- ✅ Mejor testeo y depuración

---

## 📷 Capturas de Pantalla

<img width="1174" height="763" alt="image" src="https://github.com/user-attachments/assets/8cbbac19-15a9-4459-bebb-fa4ac253a57e" />

---

## 📁 Estructura del Proyecto

app/
├── data/
│ ├── model/
│ │ ├── Car.kt
│ │ └── Purchase.kt
│ └── repository/
│ └── CarRepository.kt
├── viewmodel/
│ └── CarViewModel.kt
├── ui/
│ ├── screens/
│ │ ├── LoginScreen.kt
│ │ ├── RegisterScreen.kt
│ │ ├── ForgotPasswordScreen.kt
│ │ ├── HomeScreen.kt
│ │ ├── CarDetailScreen.kt
│ │ ├── AuctionScreen.kt
│ │ └── PaymentScreen.kt
│ └── theme/
│ ├── Color.kt
│ ├── Theme.kt
│ └── Type.kt
├── MainActivity.kt
└── AndroidManifest.xml

yaml
Copiar código

---

## ⚙️ Configuración de Gradle

### `build.gradle` (Project)
```gradle
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
build.gradle (Module: app)
gradle
Copiar código
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.luxurycar"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.3" }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
}
🔥 Configuración de Firebase
Crear proyecto en Firebase Console

Registrar app Android

Usar el mismo applicationId: com.example.luxurycar

Descargar google-services.json

Colocarlo en: app/google-services.json

📦 Modelos de Datos
Car.kt
kotlin
Copiar código
/**
 * Clase de datos que representa un automóvil en Firestore.
 *
 * @property id Identificador único del auto
 * @property brand Marca del vehículo
 * @property model Modelo del vehículo
 * @property year Año de fabricación
 * @property price Precio del vehículo
 * @property isFavorite Indica si está marcado como favorito
 */
data class Car(
    val id: String = "",
    val brand: String = "",
    val model: String = "",
    val year: Int = 0,
    val price: Double = 0.0,
    val isFavorite: Boolean = false
)
Purchase.kt
kotlin
Copiar código
/**
 * Clase que representa una compra realizada por un usuario.
 *
 * @property carId ID del auto comprado
 * @property userId ID del usuario
 * @property date Fecha de compra
 * @property total Total pagado
 */
data class Purchase(
    val carId: String = "",
    val userId: String = "",
    val date: String = "",
    val total: Double = 0.0
)
🗄️ Repositorio – Acceso a Firestore
CarRepository.kt
kotlin
Copiar código
/**
 * Repositorio encargado de manejar el acceso a Firebase Firestore.
 * Gestiona todas las operaciones CRUD y escucha cambios en tiempo real.
 */
class CarRepository {

    /** Instancia de la base de datos Firestore */
    private val db = FirebaseFirestore.getInstance()

    /**
     * Obtiene la lista de autos desde Firestore.
     *
     * @param onResult Callback que devuelve una lista de objetos Car
     */
    fun getCars(onResult: (List<Car>) -> Unit) {
        db.collection("cars")
            .addSnapshotListener { snapshot, _ ->
                val cars = snapshot?.documents?.mapNotNull {
                    it.toObject(Car::class.java)?.copy(id = it.id)
                } ?: emptyList()
                onResult(cars)
            }
    }

    /**
     * Agrega un nuevo auto a la base de datos.
     *
     * @param car Objeto Car a agregar
     */
    fun addCar(car: Car) {
        db.collection("cars").add(car)
    }

    /**
     * Elimina un auto usando su ID.
     *
     * @param id Identificador del auto a eliminar
     */
    fun deleteCar(id: String) {
        db.collection("cars").document(id).delete()
    }
}
🧠 ViewModel
CarViewModel.kt
kotlin
Copiar código
/**
 * ViewModel encargado de manejar la lógica de negocio
 * y el estado de los autos dentro de la arquitectura MVVM.
 */
class CarViewModel : ViewModel() {

    /** Repositorio que gestiona el acceso a Firebase */
    private val repository = CarRepository()

    /** StateFlow privado que almacena la lista de autos */
    private val _cars = MutableStateFlow<List<Car>>(emptyList())

    /** StateFlow público de solo lectura */
    val cars: StateFlow<List<Car>> = _cars

    /** Inicializa la lista de autos desde el repositorio */
    init {
        repository.getCars { _cars.value = it }
    }

    /**
     * Agrega un nuevo auto.
     *
     * @param car Objeto Car a agregar
     */
    fun addCar(car: Car) {
        repository.addCar(car)
    }

    /**
     * Elimina un auto por su ID.
     *
     * @param id Identificador del auto a eliminar
     */
    fun deleteCar(id: String) {
        repository.deleteCar(id)
    }
}
📱 MainActivity
kotlin
Copiar código
/**
 * Actividad principal de la aplicación.
 * Configura la UI y controla la navegación entre login y app principal.
 */
class MainActivity : ComponentActivity() {

    /** Método que se ejecuta al crear la actividad */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LuxuryCarTheme {
                AppWithLogin()
            }
        }
    }
}

/**
 * Composable que maneja la lógica de login, registro y recuperación de contraseña.
 */
@Composable
fun AppWithLogin() {
    var loggedIn by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("login") }

    if (!loggedIn) {
        when (currentScreen) {
            "login" -> LoginScreen(
                onLogin = { loggedIn = true },
                onRegister = { currentScreen = "register" },
                onForgot = { currentScreen = "forgot" }
            )
            "register" -> RegisterScreen { currentScreen = "login" }
            "forgot" -> ForgotPasswordScreen { currentScreen = "login" }
        }
    } else {
        LuxuryCarApp() // Pantalla principal después de login
    }
}
🖥️ Pantallas de la Aplicación
LoginScreen.kt
kotlin
Copiar código
@Composable
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit, onForgot: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LuxuryCar", fontSize = 44.sp, fontWeight = FontWeight.ExtraBold)
        Text("Autos de lujo y subastas en vivo", fontSize = 18.sp, color = Color.Gray)
        Spacer(Modifier.height(40.dp))

        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))

        Button(onClick = { if (user == "Juan" && pass == "1234") onLogin() else error = "Datos incorrectos" }, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Iniciar Sesión", color = Color.White, fontSize = 18.sp)
        }

        if (error.isNotEmpty()) Text(error, color = Color.Red, modifier = Modifier.padding(top = 12.dp))

        Spacer(Modifier.height(24.dp))
        Text("Crear nueva cuenta", color = Color(0xFF2563EB), modifier = Modifier.clickable { onRegister() })
        Spacer(Modifier.height(8.dp))
        Text("¿Olvidaste tu contraseña?", color = Color(0xFFDC2626), modifier = Modifier.clickable { onForgot() })
    }
}
RegisterScreen.kt
kotlin
Copiar código
@Composable
fun RegisterScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear Cuenta", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(32.dp))
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.fillMaxWidth()) {
            Text("Registrarme")
        }
        Spacer(Modifier.height(16.dp))
        Text("Volver", modifier = Modifier.clickable { onBack() })
    }
}
ForgotPasswordScreen.kt
kotlin
Copiar código
@Composable
fun ForgotPasswordScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Recuperar Contraseña", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(32.dp))
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(Color.Black), modifier = Modifier.fillMaxWidth()) {
            Text("Enviar enlace")
        }
        Spacer(Modifier.height(24.dp))
        Text("Volver", modifier = Modifier.clickable { onBack() })
    }
}
❤️ Sistema de Favoritos
kotlin
Copiar código
fun toggleFavorite(car: Car): Car {
    return car.copy(isFavorite = !car.isFavorite)
}
🎨 Theming
Color.kt: Gold y Black

Theme.kt: LuxuryCarTheme()

Type.kt: Tipografía base

🧪 Pruebas Unitarias
kotlin
Copiar código
class CarTest {
    @Test
    fun carPrice_isGreaterThanZero() {
        val car = Car(id = "1", brand = "Ferrari", model = "Roma", year = 2024, price = 250000.0)
        assertTrue(car.price > 0)
    }
}
📄 .gitignore
bash
Copiar código
.gradle/
build/
local.properties
*.iml
.idea/
.DS_Store
/captures
.externalNativeBuild
⚙️ Instrucciones de Instalación
bash
Copiar código
git clone https://github.com/tu-usuario/LuxuryCar.git
cd LuxuryCar
Abrir proyecto en Android Studio

Configurar Firebase (google-services.json)

Sincronizar Gradle

Configurar emulador o dispositivo físico

Ejecutar la app

Generar APK (Opcional)

🛠️ Solución de Problemas Comunes
Problema	Causa	Solución
Gradle no sincroniza	Caché corrupta	Invalidate Caches
SDK no encontrado	SDK mal configurado	Revisar SDK Manager
Firebase no conecta	JSON incorrecto	Revisar ruta del archivo
Emulador lento	Sin aceleración	Activar HAXM / Hyper-V
Error de dependencias	Versiones incompatibles	Revisar versiones en Gradle

📊 Validación y Métricas
Tipo: Pruebas funcionales y de usabilidad
Usuarios: 10 estudiantes
Escenario: Login → Home → Detalle → Compra

Métrica	Resultado
Usuarios participantes	10
Satisfacción general	90%
Facilidad de uso	96%
Curva de aprendizaje	84%
Diseño visual	94%
Probabilidad de recomendación	88%

👨‍💻 Autores
Jesús Antonio Romero Duarte
Rol: Desarrollador Principal
Responsabilidades: Arquitectura, Firebase, Lógica de negocio, Backend

Jonathan Andrés Arévalo Rodríguez
Rol: UI/UX Designer
Responsabilidades: Diseño de interfaces, Pruebas de usabilidad, Validación

⭐ Si este proyecto te fue útil, no olvides darle una estrella en GitHub!

Desarrollado con ❤️ por el equipo LuxuryCar
Última actualización: Diciembre 2024
