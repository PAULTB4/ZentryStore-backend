# ZentryStore Backend

Backend API para la aplicación ZentryStore construido con Spring Boot.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.5.7**
- **PostgreSQL** - Base de datos
- **Spring Security** - Autenticación y autorización
- **Flyway** - Migraciones de base de datos
- **Spring Mail** - Envío de emails
- **Lombok** - Reducción de código boilerplate
- **Spring Boot Actuator** - Monitoreo y métricas

## 📋 Requisitos Previos

- Java 17 o superior
- PostgreSQL 12 o superior
- Maven 3.8+ (opcional, el proyecto incluye Maven Wrapper)

## ⚙️ Configuración Inicial

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd ZentryStore-backend
```

### 2. Configurar Base de Datos

Crea una base de datos PostgreSQL:

```sql
CREATE DATABASE zentrystore;
```

### 3. Configurar Variables de Entorno

Copia el archivo de ejemplo y configura tus valores:

```bash
cp .env .env
```

Edita el archivo `.env` con tus configuraciones:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=zentrystore
DB_USERNAME=postgres
DB_PASSWORD=tu_password

JWT_SECRET=tu_secret_key_muy_largo_y_seguro
JWT_EXPIRATION=86400000

SERVER_PORT=8080
FRONTEND_URL=http://localhost:3000
```

📖 **Más información sobre variables de entorno:** [docs/setup/ENV_SETUP.md](docs/setup/ENV_SETUP.md)

### 4. Instalar Dependencias

```bash
# Windows
.\mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

### 5. Ejecutar la Aplicación

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/zentry/zentrystore/
│   │       ├── config/         # Configuraciones
│   │       ├── controller/     # Controladores REST
│   │       ├── model/          # Entidades JPA
│   │       ├── repository/     # Repositorios
│   │       ├── service/        # Lógica de negocio
│   │       ├── dto/            # Data Transfer Objects
│   │       ├── security/       # Configuración de seguridad
│   │       └── exception/      # Manejo de excepciones
│   └── resources/
│       ├── application.properties
│       └── db/migration/       # Migraciones Flyway
└── test/                       # Tests unitarios e integración
```

## 🔒 Seguridad

- **NO subas el archivo `.env` a Git** - Ya está en `.gitignore`
- Cambia `JWT_SECRET` en producción por una clave segura
- Usa diferentes credenciales para cada entorno

## 📚 Endpoints

Una vez la aplicación esté corriendo:

- **Health Check:** `GET /actuator/health`
- **API Docs:** _(Configurar Swagger próximamente)_

## 🧪 Tests

```bash
# Ejecutar todos los tests
.\mvnw.cmd test

# Ejecutar tests con cobertura
.\mvnw.cmd verify
```

## 🏗️ Build para Producción

```bash
.\mvnw.cmd clean package -DskipTests
```

El archivo JAR se generará en `target/zentrystore-0.0.1-SNAPSHOT.jar`

## 📖 Documentación

- [Estructura del Backend](docs/architecture/BACKEND_STRUCTURE.md)
- [Configuración de Variables de Entorno](docs/setup/ENV_SETUP.md)
- [Guía de Git Flow](docs/setup/GIT_FLOW.md)

## 🤝 Contribuir

Este proyecto usa **Git Flow**. Lee la [Guía de Git Flow](docs/setup/GIT_FLOW.md) para más detalles.

### Workflow Rápido:

```bash
# 1. Crear feature desde develop
git checkout develop
git pull origin develop
git checkout -b feature/nueva-funcionalidad

# 2. Trabajar y hacer commits
git add .
git commit -m "feat: descripción del cambio"

# 3. Terminar feature
git checkout develop
git merge feature/nueva-funcionalidad
git push origin develop
git branch -d feature/nueva-funcionalidad
```

### Convenciones de Commits:
- `feat:` Nueva funcionalidad
- `fix:` Corrección de bug
- `docs:` Actualización de documentación
- `refactor:` Refactorización de código
- `test:` Tests

## 📄 Licencia

Este proyecto es privado y confidencial.

## 👤 Autor

ZentryStore Team

