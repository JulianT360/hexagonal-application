# Hexagonal Weather Service (proyecto de práctica)
Este repositorio es un proyecto de ejemplo/ejercicio que implementa un servicio REST para consultar el clima usando una arquitectura hexagonal (ports & adapters) sobre Quarkus.

## Tabla de contenidos

- [Resumen rápido](#resumen-rápido)
- [Tecnologías y dependencias](#tecnologías-y-dependencias)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Endpoints REST](#endpoints-rest)
- [Configuración y variables de entorno](#configuración-y-variables-de-entorno)
- [Perfil de test](#perfil-de-test)
- [Construir, ejecutar y testear](#construir-ejecutar-y-testear)
- [Ejecucion Nativa con Docker](#ejecución-nativa-con-docker)

---

## Resumen rápido

- **Nombre de la aplicación** (configurable): `weather-service`
- **Puerto por defecto**: `8080` (configurable en `src/main/resources/application.yml`)
- **Endpoint principal**: `/v1/weather/current?lat={lat}&lon={lon}`

## Tecnologías y dependencias

- Java 21 (configurado en `pom.xml`)
- Quarkus (REST, REST Client, Jackson, SmallRye OpenAPI)
- MapStruct (mapeo DTO <-> dominio)
- Lombok (generación de código)
- Mutiny (Uni/Reactive usado en los puertos)
- Pruebas: JUnit 5, Quarkus JUnit5, REST-assured, Mockito
- Configuración en YAML (quarkus-config-yaml)

## Estructura del proyecto
```text
hexagonal-application/
├── docs/
│   └── postman_collection.json
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/juliantovar/arquitecturahexagonal/
│   │   │       ├── application/
│   │   │       │   ├── ports/
│   │   │       │   │   ├── in/
│   │   │       │   │   └── out/
│   │   │       │   └── service/
│   │   │       ├── domain/
│   │   │       │   ├── exception/
│   │   │       │   └── model/
│   │   │       ├── infrastructure/
│   │   │       │   ├── adapter/
│   │   │       │   │   ├── in/
│   │   │       │   │   └── out/
│   │   │       │   ├── client/
│   │   │       │   ├── config/
│   │   │       │   ├── exception/
│   │   │       │   └── rest/
│   │   │       └── shared/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       ├── java/
│       │   └── org/juliantovar/arquitecturahexagonal/
│       └── resources/
│           ├── application.yml
│           └── wiremock/
│               ├── mappings/
│               └── __files/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

### Descripción de carpetas principales

- `application`: contiene los casos de uso y puertos de entrada/salida.
- `domain`: contiene los modelos y excepciones propias del dominio.
- `infrastructure`: contiene adaptadores, clientes externos, configuración, recursos REST y mappers.
- `shared`: contiene utilidades o constantes compartidas.
- `src/test`: contiene pruebas unitarias, pruebas de integración y recursos de WireMock.

## Endpoints REST

### GET /v1/weather/current

Descripción: devuelve la información de clima actual para unas coordenadas (lat/lon).

Query parameters:
- `lat` (Double) - latitud (obligatorio)
- `lon` (Double) - longitud (obligatorio)

Respuestas principales:
- `200 OK`: JSON con información de clima (coordenadas, timezone, temperature, feelsLike, pressure, humidity, windSpeed, description)
- `400 Bad Request`: parámetros inválidos (ej. coordenadas fuera de rango)
- `401 Unauthorized`: API key inválida configurada para el proveedor externo

Ejemplo de petición (curl):

```bash
curl -s "http://localhost:8080/v1/weather/current?lat=40.4168&lon=-3.7038" -H "Accept: application/json"
```

Ejemplo de respuesta:

```json
{
  "latitude": 40.4168, 
  "longitude": -3.7038,
  "timezone": "Europe/Madrid",
  "temperature": 25.3,
  "feelsLike": 26.1,
  "pressure": 1012,
  "humidity": 40,
  "windSpeed": 3.5,
  "description": "clear sky"
}
```

## Configuración y variables de entorno

La configuración principal se encuentra en `src/main/resources/application.yml`. 
Los clientes REST externos usan claves que se resuelven desde **variables de entorno**.

- `out.weatherapi.config.api-key` → variable de entorno `WEATHER_API_KEY`

### Definir variables de entorno

Antes de ejecutar la aplicación, define las variables de entorno con tus claves:

**Windows (PowerShell):**

```powershell
$env:WEATHER_API_KEY = "tu_weatherapi_api_key"
```

**Linux / macOS (bash):**

```bash
export WEATHER_API_KEY="tu_weatherapi_api_key"
```

## Perfil de test

El proyecto define un perfil `%test` en `application.yml` que:
- Re-configura los rest-clients para apuntar a `http://localhost:9999` (mocks locales)
- Provee API keys de prueba por defecto

Esto permite ejecutar **tests de integración sin depender de servicios externos reales**.

## Construir, ejecutar y testear

Usa el **Maven wrapper** incluido en el repositorio.

### Windows (PowerShell)

```powershell
# Ejecutar todas las pruebas
mvnw.cmd test

# Compilar y empaquetar (JAR)
mvnw.cmd package

# Ejecutar en modo desarrollo (Quarkus - hot reload)
mvnw.cmd quarkus:dev
```

### Linux / macOS

```bash
# Ejecutar todas las pruebas
./mvnw test

# Compilar y empaquetar (JAR)
./mvnw package

# Ejecutar en modo desarrollo (Quarkus - hot reload)
./mvnw quarkus:dev
```

### Notas sobre ejecución

- Ejecutando `quarkus:dev`, la API estará disponible en **`http://localhost:8080`** por defecto.
- En modo desarrollo (hot reload) los cambios en el código se cargan automáticamente.
- Documentación OpenAPI: si SmallRye OpenAPI está habilitado en `pom.xml`, Quarkus expone:
  - OpenAPI JSON: `http://localhost:8080/q/openapi`
  - Swagger UI: `http://localhost:8080/q/swagger-ui`

## Ejecución Nativa con Docker

Este proyecto puede compilarse como ejecutable nativo de Quarkus y ejecutarse dentro de un contenedor Docker basado en UBI 8.

### Requisitos

- Docker instalado y en ejecución
- Maven instalado
- Java 21
- Acceso a internet para descargar dependencias e imágenes Docker
- Variables de entorno/API keys:
  - `WEATHER_API_KEY`

### 1. Generar ejecutable nativo

En PowerShell, ejecutar desde la raíz del proyecto:

```powershell
mvn clean package "-Dnative" `
  "-Dquarkus.native.container-build=true" `
  "-Dquarkus.native.container-runtime=docker" `
  "-Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21"
```
Este comando genera el binario nativo en:
`target/arquitectura-hexagonal-1.0.0-SNAPSHOT-runner`.

### 2. Construir imagen Docker

```powershell
docker build --no-cache `
  -f src/main/docker/Dockerfile.native `
  -t weather-service:native .
```

### 3. Ejecutar contenedor Docker

```powershell
docker run --rm `
  -p 8080:8080 `
  -e WEATHER_API_KEY="tu_weather_api_key" `
  weather-service:native
```

## Colección Postman
Se incluye un archivo `postman_collection.json` con ejemplos de peticiones para probar el endpoint `/v1/weather/current`. 
Puedes importarlo en Postman para realizar pruebas rápidas.
Esta ubicado en la raíz del proyecto: /docs