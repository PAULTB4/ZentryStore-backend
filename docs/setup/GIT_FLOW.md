# Git Flow - ZentryStore Backend

## 🌳 Estructura de Ramas

Este proyecto utiliza **Git Flow** para organizar el desarrollo:

```
main (producción)
  └── develop (desarrollo principal) ⭐ TU RAMA DE TRABAJO
       ├── feature/nueva-funcionalidad-1
       ├── feature/nueva-funcionalidad-2
       └── feature/nueva-funcionalidad-3
```

### Ramas Principales

- **`main`**: Código en producción (protegida)
- **`develop`**: Rama de desarrollo principal ⭐ **TRABAJA AQUÍ**

### Ramas Temporales

- **`feature/*`**: Nuevas funcionalidades

---

## 🚀 Workflow de Desarrollo

### 1. Empezar una Nueva Funcionalidad

Siempre desde `develop`:

```bash
# Asegúrate de estar en develop y actualizado
git checkout develop
git pull origin develop

# Crea una nueva rama feature
git checkout -b feature/nombre-de-funcionalidad
```

### 2. Trabajar en tu Feature

```bash
# Haz tus cambios y commits
git add .
git commit -m "feat: descripción del cambio"

# Sube tu feature al remoto (opcional, para respaldo)
git push -u origin feature/nombre-de-funcionalidad
```

### 3. Terminar el Feature

```bash
# Regresa a develop
git checkout develop

# Actualiza develop
git pull origin develop

# Mergea tu feature
git merge feature/nombre-de-funcionalidad

# Sube los cambios a develop
git push origin develop

# Elimina la rama feature (local)
git branch -d feature/nombre-de-funcionalidad

# Elimina la rama feature (remoto, si la subiste)
git push origin --delete feature/nombre-de-funcionalidad
```

---

## 📝 Convenciones de Commits

Usa [Conventional Commits](https://www.conventionalcommits.org/):

```bash
feat: agrega nueva funcionalidad
fix: corrige un bug
docs: actualiza documentación
style: cambios de formato (sin afectar código)
refactor: refactorización de código
test: agrega o modifica tests
chore: cambios en build, configuración, etc.
```

### Ejemplos:

```bash
git commit -m "feat: agrega endpoint de autenticación"
git commit -m "fix: corrige validación de email"
git commit -m "docs: actualiza README con instrucciones de setup"
git commit -m "refactor: mejora estructura de controladores"
```

---

## 🔄 Workflows Comunes

### Crear y Completar un Feature (Flujo Rápido)

```bash
# 1. Crear feature
git checkout develop
git pull origin develop
git checkout -b feature/login-usuario

# 2. Trabajar y hacer commits
git add .
git commit -m "feat: implementa login de usuario"

# 3. Terminar feature
git checkout develop
git merge feature/login-usuario
git push origin develop
git branch -d feature/login-usuario
```

### Actualizar tu Feature con cambios de Develop

Si `develop` ha avanzado mientras trabajas en tu feature:

```bash
# En tu rama feature
git checkout feature/tu-funcionalidad

# Trae cambios de develop
git pull origin develop

# Resuelve conflictos si hay
# Continúa trabajando
```

### Ver el Estado de tus Ramas

```bash
# Ver ramas locales
git branch

# Ver todas las ramas (incluyendo remotas)
git branch -a

# Ver rama actual
git status
```

---

## ⚠️ Reglas Importantes

### ✅ DO (Hacer):
- ✅ Siempre trabaja en `develop` o en `feature/*`
- ✅ Crea una `feature` para cada nueva funcionalidad
- ✅ Haz commits frecuentes y descriptivos
- ✅ Actualiza `develop` antes de crear un nuevo feature
- ✅ Prueba tu código antes de mergear a `develop`

### ❌ DON'T (No hacer):
- ❌ NO hagas commits directamente en `main`
- ❌ NO hagas push forzado (`git push -f`) en `develop` o `main`
- ❌ NO mezcles múltiples funcionalidades en un solo feature
- ❌ NO dejes features sin mergear por mucho tiempo

---

## 🎯 Ejemplos de Features

### Feature: Sistema de Autenticación
```bash
git checkout develop
git checkout -b feature/auth-system
# ... trabaja en auth ...
git add .
git commit -m "feat: implementa JWT authentication"
git checkout develop
git merge feature/auth-system
git push origin develop
git branch -d feature/auth-system
```

### Feature: CRUD de Productos
```bash
git checkout develop
git checkout -b feature/product-crud
# ... trabaja en productos ...
git add .
git commit -m "feat: agrega CRUD de productos"
git checkout develop
git merge feature/product-crud
git push origin develop
git branch -d feature/product-crud
```

---

## 🚀 Despliegue a Producción

Cuando `develop` esté estable y listo para producción:

```bash
# 1. Actualiza main con develop
git checkout main
git pull origin main
git merge develop

# 2. Crea un tag de versión
git tag -a v1.0.0 -m "Release version 1.0.0"

# 3. Sube todo
git push origin main
git push origin --tags
```

---

## 📊 Estado Actual del Proyecto

```
Rama actual: develop ⭐
Rama de producción: main
```

### Comandos de Verificación

```bash
# ¿En qué rama estoy?
git branch --show-current

# ¿Qué cambios tengo?
git status

# ¿Qué ramas existen?
git branch -a
```

---

## 🆘 Comandos de Emergencia

### Descartar cambios locales
```bash
git checkout -- .
```

### Volver al último commit
```bash
git reset --hard HEAD
```

### Cambiar de rama descartando cambios
```bash
git checkout -f develop
```

---

## 📖 Recursos

- [Git Flow Cheatsheet](https://danielkummer.github.io/git-flow-cheatsheet/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Git Documentation](https://git-scm.com/doc)

---

**¡Happy Coding! 🚀**

