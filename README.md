# Prueba tenica FNTecnologia

Este repositorio incluye:

- **Auth Service**: Maneja autenticación y generación de JWT.
- **Product Service**: Gestión de productos.
- **API Gateway**: Entrada unificada y validación de tokens JWT.
- **Frontend**: Aplicación SPA en React que consume los microservicios.

---

## Requisitos previos

- [Docker y Docker Compose](https://docs.docker.com/compose/install/) 
- Java 17
- Node.js 18+ y npm
- Maven

## Levantar bases de datos

- Ejecutar archivo docker compose 
```bash
docker-compose up -d
```

Este comando levantará:

PostgreSQL para auth-service en el puerto 5434

PostgreSQL para product-service en el puerto 5433

---

## Ejecutar microservicios

### Auth Service
```bash
cd auth-service
mvn clean package
java -jar target/auth-service.jar
```

### Auth Service
```bash
cd product-service
mvn clean package
java -jar target/product-service.jar
```

### Gateway Service
```bash
cd gateway-service
mvn clean package
java -jar target/gateway-service.jar
```

### Ejecutar el frontend
```bash
cd frontend
npm install
npm run dev
```
La aplicación se levanta en (http://localhost:5173)


## Probar los endpoints

### Front
Ingresar a la aplicacion (http://localhost:5173)

Usuario de prueba para el login:
Admin: admin@example.com - adminn
Usuario: user@example.com - 123456

### Postman
Regístrate en POST /api/auth/register
Inicia sesión en POST /api/auth/login y copia el JWT
Usar el token para consumir los endpoints protegidos