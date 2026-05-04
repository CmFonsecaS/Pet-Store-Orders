# 📦 Microservicio de Órdenes y Productos

Este microservicio ha sido desarrollado íntegramente con **Spring Boot** para centralizar la gestión de consultas relacionadas con **productos y órdenes de compra**.

El flujo de funcionamiento es sencillo: al recibir una solicitud HTTP de tipo **GET**, el sistema procesa la petición de forma inmediata y devuelve los datos en **formato JSON**, manteniendo una arquitectura **stateless (sin estado)**.

---

## 🚀 Funcionalidades disponibles

El microservicio expone diversos endpoints para la consulta de información en formato JSON.

Las funcionalidades principales incluyen:

- Listar el catálogo completo de productos.
- Filtrar productos por categoría.
- Consultar todas las órdenes registradas.
- Consultar el estado específico de una orden.

---

## 📚 Endpoints disponibles

### 1️⃣ Listar todos los productos

Permite obtener el catálogo completo de productos.

**Endpoint**

```http
GET /api/products
```

**URL de prueba**

```
http://localhost:8080/api/products
```

**Respuesta**

Retorna un **JSON con todos los productos disponibles**.

---

### 2️⃣ Filtrar productos por categoría

Permite obtener los productos pertenecientes a una categoría específica.

**Endpoint**

```http
GET /api/products/category/{nombre}
```

**URL de prueba**

```
http://localhost:8080/api/products/category/Juguetes
```

**Respuesta**

Retorna un **JSON con los productos filtrados según la categoría indicada**.

---

### 3️⃣ Listar todas las órdenes

Permite consultar todas las órdenes registradas en el sistema.

**Endpoint**

```http
GET /api/orders
```

**URL de prueba**

```
http://localhost:8080/api/orders
```

**Respuesta**

Retorna un **JSON con el listado completo de órdenes**.

---

### 4️⃣ Consultar estado de una orden

Permite consultar directamente el **estado actual de una orden específica**.

**Endpoint**

```http
GET /api/orders/{id}/status
```

**URL de prueba**

```
http://localhost:8080/api/orders/{id}/status
```

**Ejemplo**

```
http://localhost:8080/api/orders/102/status
```

**Respuesta**

Retorna un **JSON con el estado actual de la orden consultada**.

---

# Actividad Sumativa 3: Generando un microservicio de calidad y con documentación - Semana 8

## 🔐 Seguridad y Acceso

El microservicio está protegido. Todas las peticiones deben incluir las credenciales de seguridad en el encabezado:

- **Username:** `fullstack1`
- **Password:** `SumativasFullStack2026`
- **Método:** Basic Authentication

---

## 📚 Endpoints y Documentación HATEOAS

Todas las respuestas del sistema incluyen metadatos de navegación (`_links`).

### 1️⃣ Productos
- **Listar Catálogo:** `GET /api/products`
- **Filtrar por Categoría:** `GET /api/products/category/{nombre}`
- **Ejemplo HATEOAS:**
  ```json
  "_links": {
    "self": { "href": "http://localhost:8080/api/products/1" },
    "products": { "href": "http://localhost:8080/api/products" }
  }
  ```

### 2️⃣ Órdenes
- **Listar Todas:** `GET /api/orders`
- **Detalle de Orden:** `GET /api/orders/{id}`
- **Estado Específico:** `GET /api/orders/{id}/status`

---

## 🧪 Pruebas Unitarias

Para garantizar la estabilidad, el proyecto incluye una suite de pruebas que valida la lógica de negocio sin depender de la base de datos externa.

**Ejecutar pruebas:**
```bash
./mvnw test
```
*Se validan servicios de Productos y Órdenes utilizando Mocks para aislamiento total.*

---

## 🐳 Despliegue con Docker

El proyecto está configurado para ejecutarse en entornos aislados de nube mediante contenedores.

### Requisitos previos
- Tener la carpeta `Wallet_MIDBDUOC` en la raíz del proyecto (necesaria para la conexión a Oracle Cloud).

### Instrucciones de ejecución
1. **Construir y levantar el contenedor:**
   ```bash
   docker compose up -d --build
   ```
2. **Verificar estado:**
   El servicio estará disponible en `http://localhost:8080`.
3. **Logs del sistema:**
   ```bash
   docker logs -f petstore-app-1
   ```

---

## ☁️ Conexión Oracle Cloud (Wallet)
La aplicación utiliza una Oracle Wallet física inyectada en el contenedor. La ruta de configuración se gestiona dinámicamente mediante la variable de entorno `TNS_ADMIN` definida en el `Dockerfile` y `docker-compose.yml`, garantizando que las credenciales de la nube nunca se expongan en el código fuente.

---
