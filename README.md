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
