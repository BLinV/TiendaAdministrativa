# Sistema de Gestión de Productos y Categorías

Prueba técnica Full Stack. Aplicación de gestión de productos y categorías con
autenticación de usuarios, desarrollada con **Spring Boot** (backend REST) y
**Angular** (frontend SPA), sobre **MySQL**.

## Tecnologías

| Capa | Tecnología | Versión |
|------|-----------|---------|
| Backend | Java + Spring Boot | 21 / 4.1 |
| Frontend | Angular | 21 |
| Base de datos | MySQL | 8.x |
| Autenticación | JWT (JSON Web Token) | — |
| Documentación API | Swagger / OpenAPI | — |

## Estructura del repositorio

```
.
├── backend/www      → API REST (Spring Boot)
├── frontend/www     → SPA (Angular)
├── database         → scripts SQL (creación y datos de prueba)
└── README.md
```

## Requisitos previos

- **JDK 21** (probado con Liberica JDK 21 LTS de BellSoft)
- **Node.js 24** y **npm 11**
- **Angular CLI 21**: `npm install -g @angular/cli`
- **MySQL 8** en ejecución (local, p. ej. mediante Laragon/XAMPP)

## 1. Base de datos

Ejecutar los scripts en orden, desde un cliente MySQL con permisos de administrador:

```sql
-- 1. Crea la base de datos y los usuarios de la aplicación
source database/01-init.sql

-- 2. Crea las tablas (esquema)
source database/02-schema.sql

-- 3. Inserta datos de prueba
source database/03-data.sql
```

> **Nota de seguridad:** el script `01-init.sql` crea los usuarios de MySQL con
> sus contraseñas. Son credenciales de **entorno local desechable**. En un
> despliegue real se externalizarían mediante variables de entorno o un gestor
> de secretos. El usuario que usa la aplicación (`tienda_user`) solo tiene
> permisos de datos (SELECT/INSERT/UPDATE/DELETE), no de estructura.

## 2. Backend (Spring Boot)

Desde `backend/www`:

```bash
./mvnw spring-boot:run     # levanta la API en http://localhost:8080
```

La configuración de conexión está en `src/main/resources/application.yaml`.
Si tu MySQL usa credenciales distintas a las del script, ajústalas ahí.

### Pruebas

```bash
./mvnw test                       # unitarias (JUnit + Mockito) — no requiere base de datos
./mvnw test -Dgrupos.excluidos=   # añade las de integración — requiere MySQL en marcha
```

Las unitarias aíslan cada servicio con Mockito y terminan en unos segundos.

`WwwApplicationTests` está etiquetado como `integracion` y **queda excluido por
defecto**: arranca el contexto completo de Spring y, gracias a
`ddl-auto: validate`, comprueba que el mapeo JPA cuadra con el esquema real. Por
eso necesita la base de datos creada y el servidor MySQL en ejecución.

### Documentación de la API (Swagger)

Con el backend en ejecución: **http://localhost:8080/swagger-ui/index.html**

La API está protegida con JWT. Para probar endpoints protegidos desde Swagger:

1. Ejecutar `POST /api/auth/login` con un usuario válido (ver más abajo).
2. Copiar el `token` de la respuesta.
3. Pulsar **Authorize** (arriba a la derecha) y pegar el token.

## 3. Frontend (Angular)

Desde `frontend/www`:

```bash
npm install     # instala dependencias (solo la primera vez)
npm start       # levanta la app en http://localhost:4200
```

El frontend consume la API en `http://localhost:8080/api` (configurable en
`src/environments/`).

## Usuarios de prueba

| Usuario | Contraseña |
|---------|-----------|
| admin | admin123 |
| vendedor | vendedor123 |
| cliente | cliente123 |

## Funcionalidades

- **Autenticación** con JWT (login, token en cabecera `Authorization`).
- **CRUD de productos** (nombre, descripción, precio, stock, categoría).
- **CRUD de categorías** (nombre, descripción).
- **CRUD de usuarios** (con contraseña cifrada mediante BCrypt).
- **Búsqueda de productos** por categoría, precio máximo y nombre (filtros combinables),
  resuelta con un `JOIN FETCH` que trae la categoría en la misma consulta.
- **Validaciones** de campos y **respuestas de error uniformes**: todas comparten la
  forma `{"mensaje": "..."}`, y las de validación añaden `campos` con el detalle por
  campo. Códigos usados: 400, 401, 404 y 409.
- **Vista detalle** de producto y componente de tarjeta reutilizable.
- **Notificaciones** de éxito/error en las operaciones.

## Orden de arranque

1. Iniciar **MySQL** y ejecutar los scripts de `database/`.
2. Iniciar el **backend** (`./mvnw spring-boot:run`).
3. Iniciar el **frontend** (`npm start`).
4. Abrir **http://localhost:4200** e iniciar sesión con un usuario de prueba.
