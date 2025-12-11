# # 🚗 LuxuryCar: Plataforma Global de Subastas y Venta de Vehículos de Lujo

[![Estado del Proyecto](https://img.shields.io/badge/Estado-Producci%C3%B3n%20%2F%20Beta-blue.svg)](https://app.luxurycar.com/status)
[![Licencia](https://img.shields.io/badge/Licencia-PROPIETARIA-red.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Pasada-green.svg)](https://example.com/build)

Aplicación nativa y web desarrollada con un *stack* moderno de microservicios para gestionar transacciones de alto valor y subastas digitales en vivo con latencia mínima. El sistema prioriza la seguridad transaccional, la verificación de terceros y el servicio de conserjería (*concierge*) post-venta.

## 🚀 Características Principales del Sistema

El proyecto implementa todas las entidades clave (USUARIO, AUTO, SUBASTA, PUJA, PAGO) con enfoque en el rendimiento y la seguridad:

* **Subastas Digitales en Vivo (Low Latency):** Gestión de ofertas (`PUJA`) en tiempo real con latencia inferior a 500ms, esencial para la fidelidad de la subasta.
* **Microservicio de Verificación:** Módulo dedicado a procesar y adjuntar informes de terceros (condición mecánica, estatus legal) a cada `AUTO`, alimentando la confianza.
* **Sistema de Escrow y Pagos:** Gestión segura de fondos (`PAGO`) con integración a plataformas financieras, garantizando la retención del dinero hasta la transferencia del vehículo.
* **Listas de Favoritos y Alertas:** Notificaciones en tiempo real sobre nuevas pujas o cambios de estatus en autos marcados como `FAVORITO`.
* **API Gateway:** Punto de entrada único y seguro para los clientes móviles y web.
* **Servicio de Logística:** Módulo para coordinar el servicio de conserjería (transporte asegurado y titulación).

## 🛠️ Tecnologías Utilizadas y Arquitectura

El proyecto sigue una arquitectura de **Microservicios** para garantizar escalabilidad y tolerancia a fallos, utilizando el patrón **DDD (Domain-Driven Design)**.

| Componente | Tecnología Clave | Uso Principal |
| :--- | :--- | :--- |
| **Frontend (Mobile/Web)** | **React Native** / **TypeScript** | Interfaz de usuario declarativa y multiplataforma. |
| **Backend Core** | **Node.js** (TypeScript) / **Express** | Lógica de negocio y APIs REST/GraphQL. |
| **Base de Datos (Transaccional)** | **PostgreSQL** | Almacenamiento de `USUARIO`, `AUTO`, `PAGO` y relaciones (`FAVORITO`). |
| **Real-Time / Eventos** | **Apache Kafka** / **Redis Pub/Sub** | Streaming de datos de pujas en tiempo real y notificaciones críticas. |
| **Contenedores** | **Docker** / **Kubernetes (K8s)** | Orquestación, despliegue y escalabilidad de los microservicios. |
| **Cloud Computing** | **AWS / GCP** | Servicios de *hosting*, almacenamiento de medios (S3) y balanceo de carga. |

## ⚙️ Estructura del Proyecto

El proyecto está dividido en varios repositorios que corresponden a los microservicios.
## 🚀 Cómo Ejecutar el Proyecto (Desarrollo Local)

Para levantar el ecosistema completo de microservicios, se utiliza Docker Compose.

1.  **Requisitos:**
    * Docker y Docker Compose instalados.
    * Node.js (v18+) y TypeScript.

2.  **Configuración de Entorno:**
    * Clona el repositorio.
    * Crea el archivo `.env` en el directorio raíz (`/services/`) con las credenciales de PostgreSQL (ver plantilla `.env.example`).
    * Configura las claves de API de AWS/GCP para servicios de almacenamiento de imágenes.

3.  **Levantar el Entorno:**
    ```bash
    # Construye y levanta todos los contenedores de microservicios, PostgreSQL y Kafka
    docker-compose up --build -d
    
    # Instala dependencias en el cliente móvil
    cd mobile-app
    npm install
    npx react-native run-ios # o run-android
    ```

## 🔒 Reglas de Seguridad Clave

Dada la naturaleza de alto valor de las transacciones:

* Las reglas de la base de datos fuerzan la integridad referencial de `PAGO`, `USUARIO` y `AUTO`.
* El *payments-service* solo permite transacciones verificadas por un servicio externo (Stripe/Adyen).
* El acceso a la gestión de `VENDEDOR` está restringido por roles.

## 🤝 Contribuciones

Agradecemos contribuciones enfocadas en la optimización de la latencia en el *auctions-service* y la seguridad del *payments-service*.

Por favor, lea la [GUÍA DE CONTRIBUCIÓN](CONTRIBUTING.md) antes de abrir un *Pull Request*.

---
📧 **Contacto:**
Soporte Técnico: gtid143@gmail.com
Desarrollado por: TPM.com
LuxuryCar © 2025
