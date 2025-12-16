🚗 Aplicación de Venta de Carros LuxuryCar
🧩 PARTE 3 – TESTS, INSTALACIÓN, VALIDACIÓN, PROBLEMAS, AUTORES Y CIERRE FINAL
🧪 Pruebas (Tests)
Prueba Unitaria Básica
import org.junit.Test
import org.junit.Assert.*

class CarTest {

    @Test
    fun carPrice_isGreaterThanZero() {
        val car = Car(
            id = "1",
            brand = "Ferrari",
            model = "Roma",
            year = 2024,
            price = 250000.0
        )

        assertTrue(car.price > 0)
    }
}


📌 Explicación:

Verifica la integridad del modelo Car

Asegura que el precio sea válido

Base para futuras pruebas automatizadas

📄 Archivo .gitignore
.gradle/
build/
local.properties
*.iml
.idea/
.DS_Store
/captures
.externalNativeBuild


📌 Explicación:
Evita subir archivos temporales, configuraciones locales y caché al repositorio.

⚙️ INSTRUCCIONES DE INSTALACIÓN (COMPLETAS)
📋 Requisitos Previos

💻 Windows / macOS / Linux

☕ JDK 17 o superior

🛠️ Android Studio Hedgehog (2023.1.1) o superior

📱 Android SDK 34

🤖 Emulador o dispositivo físico con Android 7.0 (API 24) o superior

🔥 Cuenta activa de Firebase

📥 Paso 1: Clonar el Repositorio
git clone https://github.com/tu-usuario/LuxuryCar.git
cd LuxuryCar

📂 Paso 2: Abrir en Android Studio

Abrir Android Studio

Seleccionar Open

Elegir la carpeta del proyecto

Esperar sincronización de Gradle

🔥 Paso 3: Configurar Firebase

Entrar a Firebase Console

Crear proyecto nuevo

Registrar app Android

Usar este applicationId:

applicationId = "com.example.luxurycar"


Descargar google-services.json

Colocarlo en:

app/google-services.json

📦 Paso 4: Sincronizar Gradle

File > Sync Project with Gradle Files

Si falla:

File > Invalidate Caches / Restart

📱 Paso 5: Emulador o Dispositivo
Opción A – Emulador

Tools > Device Manager

Crear dispositivo virtual

API 24+

Opción B – Dispositivo Físico

Activar opciones de desarrollador

Habilitar depuración USB

Conectar por USB

▶️ Paso 6: Ejecutar la App

Seleccionar dispositivo

Presionar ▶ Run

Esperar compilación e instalación

📦 Paso 7: Generar APK (Opcional)

Build > Build Bundle(s) / APK(s) > Build APK(s)

Esperar proceso

Click en Locate

🛠️ SOLUCIÓN DE PROBLEMAS COMUNES
Problema	Causa	Solución
Gradle no sincroniza	Caché corrupta	Invalidate Caches
SDK no encontrado	SDK mal configurado	Revisar SDK Manager
Firebase no conecta	JSON incorrecto	Revisar ruta
Emulador lento	Sin aceleración	Activar HAXM / Hyper-V
Error de dependencias	Versiones incompatibles	Revisar Gradle
📊 VALIDACIÓN Y MÉTRICAS
🔍 Metodología

Tipo: Pruebas funcionales y de usabilidad

Usuarios: Estudiantes universitarios

Entorno: Emulador y dispositivo físico

Escenario: Login → Home → Detalle → Compra

📈 Resultados
Métrica	Resultado
Usuarios	10
Satisfacción general	90%
Facilidad de uso	96%
Aprendizaje	84%
Diseño visual	94%
Recomendación	88%
💬 Comentarios Reales

“La aplicación es muy intuitiva.”
“El diseño se siente profesional.”
“Sería bueno agregar comparación de autos.”

👨‍💻 AUTORES

Este proyecto fue desarrollado por:

Jesús Antonio Romero Duarte
Desarrollador Principal – Arquitectura, Firebase, Lógica, Backend

Jonathan Andrés Arévalo Rodríguez
UI / UX – Pruebas, Validación y Diseño
