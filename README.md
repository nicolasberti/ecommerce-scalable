# Proyecto "ecommerce-scalable"

## Descripción
Plataforma de comercio electrónico desarrollada bajo la arquitectura de microservicios.

En construcción... 🚀⏳

## Arquitectura del Sistema
![Arquitectura del sistema](imagenes/arquitectura.jpg)
## Transacción crear órden asincrónica (con estado pendiente) - Patrón SAGA
![Transacción crear órden asincronica (con estado pendiente)](imagenes/orden_asincronica.jpg)

## Tecnologías y herramientas utilizadas
- Spring Boot (Java)
- Node.js (Typescript)
- Nginx
- Docker
- Eureka Server and Client
- JWT
- MySQL
- Redis
- MongoDB
- Kafka
- HTTP

## Endpoints
- POST /api/users/register
- POST /api/users/login
- GET /api/products
- POST /api/products
- DELETE /api/products/<producto_id>
- GET /api/categorias
- POST /api/categorias
- DELETE /api/categorias/<categoria_id>
- PUT /api/cart/<user_id>
- GET /api/cart/<user_id>
- DELETE /api/cart/<user_id>
- POST /api/orders/<user_id>
- POST /api/orders/wait/<user_id>
- PUT /api/orders/<user_id>
- GET /api/orders/<user_id>
- GET /api/orders
  
Payment y Notification Service se manejan de manera interna por Kafka.
