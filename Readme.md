# Library Book Rental — Arquitectura Hexagonal

> Sistema de gestion de alquiler de libros construido con Spring Boot y Arquitectura Hexagonal (Ports & Adapters).

---

## Objetivos del Proyecto

- Mantener **independencia del dominio** respecto a frameworks y tecnologias externas.
- Facilitar **pruebas unitarias e integracion** sin dependencias de infraestructura.
- Permitir **intercambiar tecnologias** (DB, mensajeria, etc.) sin afectar la logica de negocio.
- Mejorar la **mantenibilidad** y **escalabilidad** del sistema.
- Exponer un API REST documentada con **OpenAPI/Swagger**.

---

## Estructura de Capas

```
src/main/java/com/miempresa/miapp/
│
├── domain/           # Entidades, Value Objects, puertos — logica pura de negocio
├── application/      # Casos de uso, Commands y DTOs — orquestacion de la logica
└── infraestructure/  # Adaptadores a tecnologias externas (DB, APIs, mensajeria)
```

### Domain
- **Entities** — Objetos con identidad propia (ej. `Book`, `Client`).
- **Value Objects** — Objetos sin identidad, definidos solo por sus valores.
- **Puertos** — Contratos/interfaces para persistencia y servicios externos.

### Application
- **Use Cases** — Orquestan las operaciones de negocio.
- **Commands** — Instrucciones especificas por caso de uso (ej. `UpdateBookCommand`).
- **DTOs** — Transportan datos entre capas (ej. `BookRecordDto`).

### Infrastructure
- **Adaptadores** — Implementaciones concretas de los puertos (ej. `BookRepositoryJPAAdapter`).
- **Mappers** — Transforman objetos entre capas (`BookEntity <-> Book <-> BookRecordDto`).
- **Controladores REST** — Exponen endpoints para clientes externos.

---

## Manejo de Excepciones

Se utiliza un `@ControllerAdvice` global para manejar todas las excepciones del sistema.

- `400 BAD_REQUEST` — **01** — Datos invalidos
- `401 UNAUTHORIZED` — **02** — No autorizado
- `404 NOT_FOUND` — **03** — Recurso no encontrado
- `404 NOT_FOUND` — **04** — Valor no encontrado en el dominio
- `500 INTERNAL_SERVER_ERROR` — **05** — Error interno de infraestructura

**Ejemplo de respuesta de error:**

```json
{
  "codigo": "01",
  "mensaje": "Formato de email invalido",
  "detalle": "El email ingresado no cumple el patron esperado"
}
```

---

## APIs Disponibles

Base URL: `http://localhost:8080` (perfil H2) | `http://localhost:8081` (perfil MySQL)

---

### Clients — `/api/v1/clients`

- `POST    /api/v1/clients`               — Crear un nuevo cliente
- `GET     /api/v1/clients/{id}`          — Obtener cliente por ID (UUID)
- `PUT     /api/v1/clients/{id}`          — Actualizar datos de un cliente
- `DELETE  /api/v1/clients/{id}`          — Desactivar cliente (no elimina fisicamente)
- `GET     /api/v1/clients/dni/{dni}`     — Obtener cliente por DNI

**Body — POST / PUT:**

```json
{
  "age": 25,
  "dni": "1718317561",
  "name": "gracie",
  "surname": "vallejo"
}
```

---

### Books — `/api/v1/books`

- `POST  /api/v1/books`                              — Crear un nuevo libro
- `GET   /api/v1/books/{id}`                         — Obtener libro por ID (UUID)
- `PUT   /api/v1/books/{id}`                         — Actualizar datos de un libro
- `GET   /api/v1/books/available`                    — Listar todos los libros disponibles
- `GET   /api/v1/order-books/borrowed/client/{dni}`  — Listar libros prestados por DNI del cliente

**Body — POST (Crear libro):**

```json
{
  "author": "string",
  "isbn": "string",
  "title": "string"
}
```

**Body — PUT (Actualizar libro):**

```json
{
  "author": "jose",
  "bookStatus": "AVAILABLE",
  "isbn": "022384179",
  "title": "arquitectura hexagonal"
}
```

---

### Order Books — `/api/v1/order-books`

- `POST  /api/v1/order-books`               — Crear solicitud de alquiler via HTTP
- `POST  /api/v1/order-book/request` *(Kafka)*     — Crear solicitud de alquiler via Kafka
- `GET   /api/v1/order-books/{id}`          — Obtener solicitud por ID
- `GET   /api/v1/order-books/cliente/{id}`  — Obtener solicitudes por DNI del cliente

**Body — POST (HTTP y Kafka):**

```json
{
  "books": [
    { "id": "uuid-del-libro-1" },
    { "id": "uuid-del-libro-2" }
  ],
  "client": {
    "id": "uuid-del-cliente"
  }
}
```

---

## Ejecucion Local — Sin Docker Compose

Nota: para cualquiera de los siguientes pasos se debe ubicar en la raiz del proyecto descargado

### 1. Clonar el repositorio

```bash
git clone --branch library-con-kafka-arq-hexagonal_v1 https://github.com/sebashh26/library-arq-hexagonal_v1.git
```

### 2. Requisitos previos

- **Docker Desktop** o motor de Docker instalado localmente.

### 3. Crear y levantar la base de datos

```bash
# Crear la imagen de MariaDB
docker build -t sebashh26/librarydb -f Dockerfile.mysqldb .

# Levantar el contenedor
docker run --name librarybd_v1 -e MARIADB_ROOT_PASSWORD=toor -e MARIADB_DATABASE=librarybd -v mariadb_data:/var/lib/mysql -p 3306:3306 -d sebashh26/librarydb
```

### 4. Compilar y ejecutar

```bash
# Compilar
mvn clean install

# Perfil MySQL
mvn spring-boot:run -Dspring-boot.run.profiles=mysql

# Perfil H2 (en memoria)
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

### 5. Explorar la base de datos

```bash
docker exec -it librarybd_v1 bash
mysql -u root -ptoor

SHOW DATABASES;
USE librarybd;
SHOW TABLES;
SELECT * FROM client;
```

### 6. Como se hizo la exportación del dump de la BD

```bash
docker exec librarybd_v1 mysqldump -uroot -p'toor' librarybd > librarybd.sql
```

### 7. Importar coleccion Postman

Importar el archivo: `Library Orden System API_con_Kafka_v1.postman_collection`

---

## Ejecucion Local — Con Docker Compose

### 1. Clonar el repositorio

```bash
git clone --branch library-con-kafka-arq-hexagonal_v1 https://github.com/sebashh26/library-arq-hexagonal_v1.git
```

### 2. Levantar los servicios

```bash
# Perfil H2
docker-compose -f docker-compose-library.yml up app-h2 zookeeper kafka kafdrop

# Perfil MySQL
docker-compose -f docker-compose-library.yml up app-mysql mysqldb zookeeper kafka kafdrop
```

### 3. Configuracion de perfiles

- Perfil `H2` — puerto **8080** — base de datos en memoria — data inicial en carpeta `/h2`
- Perfil `MySQL` — puerto **8081** — MariaDB persistente — data inicial via script SQL

---

## Notas de Infraestructura

### Creación de las Imagenes multiplataforma usadas en el docker compose

```bash
# Imagen base de datos
docker buildx build --push --platform linux/amd64,linux/arm64 -f Dockerfile.mysqldb -t sebashh26/library-db:v1 .

# Imagen aplicacion Java
docker buildx build --push --platform linux/amd64,linux/arm64 -f Dockerfile.app -t sebashh26/library-app:v1 .
```

### Kafdrop

Herramienta web para visualizar los mensajes enviados a Kafka.
Disponible en `http://localhost:9000` una vez levantado Docker Compose.

### OPEN API

Herramienta web para revisar la documentación del proyecto donde estan los request e información de ejemplo.
Disponible en `http://localhost:8080/swagger-ui/index.htm` una vez levantado Docker Compose, el puerto depende del perfil levantado