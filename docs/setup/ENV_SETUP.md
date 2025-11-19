# Configuración de Variables de Entorno

## Archivo .env

Este proyecto utiliza un archivo `.env` para configurar variables sensibles que no deben ser versionadas en Git.

### Configuración Inicial

1. **Copia el archivo de ejemplo:**
   ```bash
   cp .env.example .env
   ```

2. **Edita el archivo `.env` con tus valores reales:**
   - Configuración de base de datos
   - Secret key para JWT
   - Credenciales de email
   - Otras configuraciones sensibles

### Variables Disponibles

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `DB_HOST` | Host de PostgreSQL | `localhost` |
| `DB_PORT` | Puerto de PostgreSQL | `5432` |
| `DB_NAME` | Nombre de la base de datos | `zentrystore` |
| `DB_USERNAME` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | - |
| `SPRING_PROFILES_ACTIVE` | Perfil de Spring activo | `dev` |
| `JWT_SECRET` | Secret key para tokens JWT | - |
| `JWT_EXPIRATION` | Tiempo de expiración JWT (ms) | `86400000` |
| `SERVER_PORT` | Puerto del servidor | `8080` |
| `FRONTEND_URL` | URL del frontend para CORS | `http://localhost:3000` |
| `MAIL_HOST` | Host del servidor de email | `smtp.gmail.com` |
| `MAIL_PORT` | Puerto del servidor de email | `587` |
| `MAIL_USERNAME` | Usuario de email | - |
| `MAIL_PASSWORD` | Contraseña de email | - |

### Notas de Seguridad

⚠️ **NUNCA** subas el archivo `.env` a Git. Este archivo ya está incluido en `.gitignore`.

✅ **SÍ** sube el archivo `.env.example` como plantilla para otros desarrolladores.

### Ejemplo de .env

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=zentrystore
DB_USERNAME=postgres
DB_PASSWORD=mi_password_seguro

JWT_SECRET=mi_secret_key_muy_largo_y_seguro_cambiar_en_produccion
JWT_EXPIRATION=86400000

SERVER_PORT=8080
FRONTEND_URL=http://localhost:3000
```

### En Producción

En entornos de producción (como Heroku, AWS, Azure, etc.), configura estas variables directamente en el panel de configuración de tu proveedor de hosting, no uses archivos `.env`.

