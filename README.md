# Repaso API - Spring Boot

API REST construida con Spring Boot para gestionar:
- Usuarios
- Clientes
- Sucursales
- Pedidos
- Articulos de pedidos
- Articulos despachados

Incluye:
- Spring Security
- Autenticacion JWT
- Validacion de requests con `@Valid`
- Respuesta estandarizada para exito y error

## Stack
- Java 17
- Spring Boot 4.0.3
- Spring Web
- Spring Data JPA
- Spring Security
- Bean Validation
- JWT (`jjwt`)
- MySQL
- Maven Wrapper (`mvnw`)

## Ejecutar proyecto
1. Configura base de datos en `src/main/resources/application.yml`.
2. Ejecuta:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\\mvnw.cmd spring-boot:run
```

Servidor por defecto:
- `http://localhost:8085`

## Seguridad y autenticacion
### Registro de usuario (publico)
- `POST /api/usuarios`

### Login (publico)
- `POST /api/auth/login`

Body ejemplo:

```json
{
  "email": "usuario@correo.com",
  "password": "password123"
}
```

Respuesta:

```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "token": "eyJ..."
  },
  "timestamp": "2026-02-27T18:00:00"
}
```

### Consumir endpoints protegidos
Enviar header:

```http
Authorization: Bearer <token>
```

## Formato de respuesta
### Exito

```json
{
  "success": true,
  "message": "Mensaje de operacion",
  "data": {},
  "timestamp": "2026-02-27T18:00:00"
}
```

### Error

```json
{
  "success": false,
  "status": 400,
  "error": "Bad Request",
  "message": "Validacion fallida",
  "path": "/api/sucursales",
  "errors": {
    "nombreSucursal": "El nombreSucursal es obligatorio"
  },
  "timestamp": "2026-02-27T18:00:00"
}
```

## Endpoints principales
### Usuarios
- `POST /api/usuarios`
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

### Clientes
- `POST /api/clientes`
- `GET /api/clientes`
- `GET /api/clientes/{rut}`
- `PUT /api/clientes/{rut}`
- `DELETE /api/clientes/{rut}`

### Sucursales
- `POST /api/sucursales`
- `GET /api/sucursales`
- `GET /api/sucursales/{id}`
- `PUT /api/sucursales/{id}`
- `DELETE /api/sucursales/{id}`
- `GET /api/sucursales/con-pedidos`

### Pedidos
- `POST /api/pedidos`
- `GET /api/pedidos`
- `GET /api/pedidos/{id}`
- `GET /api/pedidos/cliente/{rut}`
- `PUT /api/pedidos/{id}`
- `DELETE /api/pedidos/{id}`

### Articulos de pedidos
- `POST /api/articulos-pedidos`
- `GET /api/articulos-pedidos`
- `GET /api/articulos-pedidos/pedido/{idPedido}`
- `PUT /api/articulos-pedidos`
- `DELETE /api/articulos-pedidos?idPedido={idPedido}&idArticulo={idArticulo}`

### Articulos despachados
- `POST /api/articulos-despachos`
- `GET /api/articulos-despachos`
- `GET /api/articulos-despachos/{id}`
- `GET /api/articulos-despachos/pedido/{idPedido}`
- `PUT /api/articulos-despachos/{id}`
- `DELETE /api/articulos-despachos/{id}`

## Notas
- Las validaciones se aplican sobre DTOs `request` con Bean Validation.
- La password del usuario se almacena encriptada con BCrypt.
- El campo `trace` fue deshabilitado en respuestas de error.
