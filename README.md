# 📦 Microservicio Petstore

Este microservicio es una evolución para la gestión de **Productos** y **Órdenes de Compra**, diseñado en **Spring Boot**, persistencia en **Oracle Cloud** y protocolos de seguridad.

---

## 🚀 Características Principales

- **Persistencia Real**: Conexión a **Oracle Cloud Autonomous Database** mediante Oracle Wallet.
- **Seguridad**: Implementación de **Spring Security** con Autenticación Básica (Basic Auth).
- **CRUD Completo**: Gestión de productos y órdenes (Crear, Leer, Actualizar, Eliminar).
- **Validación de Datos**: Uso de `@Valid` y DTOs para asegurar la integridad de cada campo.
- **Manejo de Errores**: Sistema global de excepciones que devuelve respuestas JSON estandarizadas.
- **Arquitectura**: Separación clara entre Controladores, Servicios, Repositorios (JPA) y Modelos.

---

## 🔒 Seguridad

El microservicio está protegido. Para cualquier consumo de API, se deben utilizar las siguientes credenciales mediante **Basic Auth**:

- **Usuario**: `fullstack1`
- **Contraseña**: `SumativasFullStack2026`

---

## 📚 Documentación de la API (Endpoints)

### 🛒 Productos (`/api/products`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/products` | Listar todo el catálogo. |
| **GET** | `/api/products/{id}` | Obtener un producto por su ID. |
| **GET** | `/api/products/category/{name}` | Filtrar productos por categoría. |
| **POST** | `/api/products` | Crear un nuevo producto (requiere JSON). |
| **PUT** | `/api/products/{id}` | Actualizar un producto existente. |
| **DELETE** | `/api/products/{id}` | Eliminar un producto. |

### 📋 Órdenes (`/api/orders`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/orders` | Listar todas las órdenes registradas. |
| **GET** | `/api/orders/{id}` | Obtener detalle de una orden específica. |
| **GET** | `/api/orders/{id}/status` | Consultar solo el estado de una orden. |
| **POST** | `/api/orders` | Registrar una nueva orden (con productos). |
| **PUT** | `/api/orders/{id}` | Actualizar datos de una orden. |
| **DELETE** | `/api/orders/{id}` | Eliminar una orden (borrado en cascada). |

---

## ⚙️ Configuración del Entorno

### Base de Datos Oracle Cloud
La conexión se realiza mediante **Oracle Wallet**. Asegúrate de que el archivo `application.properties` apunte correctamente a la ruta de tu Wallet local:

```properties
spring.datasource.url=jdbc:oracle:thin:@midbduoc_high?TNS_ADMIN=C:/ruta/a/tu/wallet
spring.datasource.username=fullstack1
spring.datasource.password=SumativasFullStack2026
```

### Inicialización
1. Ejecuta el script SQL proporcionado, para crear las tablas y cargar datos iniciales en Oracle.

## Script SQL de creación y carga inicial

```sql
-- ==========================================================
-- SCRIPT DE INICIALIZACIÓN - PETSTORE ORDERS (SQL DEVELOPER)
-- ==========================================================

-- 1. Eliminación de tablas si existen (en orden inverso de dependencia)
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE ORDER_ITEMS CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE ORDERS CASCADE CONSTRAINTS';
   EXECUTE IMMEDIATE 'DROP TABLE PRODUCTS CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 2. Creación de Tabla de Productos
CREATE TABLE PRODUCTS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME VARCHAR2(100) NOT NULL,
    CATEGORY VARCHAR2(50) NOT NULL,
    PRICE NUMBER NOT NULL,
    STOCK NUMBER NOT NULL,
    BRAND VARCHAR2(50),
    DESCRIPTION VARCHAR2(255),
    PET_TYPE VARCHAR2(50)
);

-- 3. Creación de Tabla de Órdenes
CREATE TABLE ORDERS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ORDER_DATE DATE DEFAULT SYSDATE NOT NULL,
    CUSTOMER_NAME VARCHAR2(100) NOT NULL,
    CUSTOMER_EMAIL VARCHAR2(100) NOT NULL,
    SHIPPING_ADDRESS VARCHAR2(255) NOT NULL,
    TOTAL_AMOUNT NUMBER NOT NULL,
    STATUS VARCHAR2(50) NOT NULL
);

-- 4. Creación de Tabla de Items de Orden
CREATE TABLE ORDER_ITEMS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ORDER_ID NUMBER NOT NULL,
    PRODUCT_ID NUMBER NOT NULL,
    PRODUCT_NAME VARCHAR2(100) NOT NULL,
    QUANTITY NUMBER NOT NULL,
    UNIT_PRICE NUMBER NOT NULL,
    CONSTRAINT FK_ORDER FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID) ON DELETE CASCADE
);

-- 5. Carga Inicial de Productos (Mínimo 3)
INSERT INTO PRODUCTS (NAME, CATEGORY, PRICE, STOCK, BRAND, DESCRIPTION, PET_TYPE) 
VALUES ('Comida Premium Perro', 'Nutrición', 4500, 100, 'BarkMaster', 'Kibble nutritivo', 'Perro');

INSERT INTO PRODUCTS (NAME, CATEGORY, PRICE, STOCK, BRAND, DESCRIPTION, PET_TYPE) 
VALUES ('Juguete Plumas Gato', 'Juguetes', 1500, 50, 'KittyFun', 'Varita interactiva', 'Gato');

INSERT INTO PRODUCTS (NAME, CATEGORY, PRICE, STOCK, BRAND, DESCRIPTION, PET_TYPE) 
VALUES ('Shampoo Orgánico', 'Higiene', 2200, 30, 'CleanPet', 'Aloe vera natural', 'Perro/Gato');

-- 6. Carga Inicial de Órdenes (Mínimo 3)
INSERT INTO ORDERS (CUSTOMER_NAME, CUSTOMER_EMAIL, SHIPPING_ADDRESS, TOTAL_AMOUNT, STATUS) 
VALUES ('Juan Pérez', 'juan.perez@gmail.com', 'Calle Falsa 123', 6000, 'ENTREGADO');

INSERT INTO ORDERS (CUSTOMER_NAME, CUSTOMER_EMAIL, SHIPPING_ADDRESS, TOTAL_AMOUNT, STATUS) 
VALUES ('Maria Garcia', 'maria@yahoo.com', 'Avenida Siempreviva 742', 1500, 'ENVIADO');

INSERT INTO ORDERS (CUSTOMER_NAME, CUSTOMER_EMAIL, SHIPPING_ADDRESS, TOTAL_AMOUNT, STATUS) 
VALUES ('Carlos Soto', 'csoto@gmail.com', 'Pasaje Los Olivos 45', 2200, 'PROCESANDO');

-- 7. Carga Inicial de Items de Orden (Referenciando las órdenes creadas)
-- Maria (Orden 1) compró comida y shampoo
INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, UNIT_PRICE) VALUES (1, 1, 'Comida Premium Perro', 1, 4500);
INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, UNIT_PRICE) VALUES (1, 3, 'Shampoo Orgánico', 1, 1500);

-- Maria (Orden 2) compró juguete
INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, UNIT_PRICE) VALUES (2, 2, 'Juguete Plumas Gato', 1, 1500);

-- Carlos (Orden 3) compró shampoo
INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, UNIT_PRICE) VALUES (3, 3, 'Shampoo Orgánico', 1, 2200);

COMMIT;
```

---

## 🚀 Ejecución del Proyecto

1. Tener configurada la ruta de la Wallet de Oracle en el equipo.

---
