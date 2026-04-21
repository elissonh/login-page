# Login App — Backend (Spring Boot)

## How to run

**Prerequisites:** Java 17+ and Maven installed.

```bash
cd backend
mvn spring-boot:run
```

Server starts at: **http://localhost:8080**

---

## Endpoints

| Method | URL | Body | Description |
|--------|-----|------|-------------|
| `GET`  | `/api/auth/health` | — | Check server is running |
| `POST` | `/api/auth/register` | `{ "username": "john", "password": "secret123" }` | Create account |
| `POST` | `/api/auth/login` | `{ "username": "john", "password": "secret123" }` | Login |

### H2 Database Console (dev only)
URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:logindb`
- Username: `sa`
- Password: *(leave blank)*

---

## Project structure explained

```
src/main/java/com/loginapp/
│
├── LoginAppApplication.java      ← Entry point, starts the server
│
├── model/
│   └── User.java                 ← Database entity (maps to "users" table)
│
├── dto/
│   └── AuthDTOs.java             ← Request/Response shapes for the API
│
├── repository/
│   └── UserRepository.java       ← Database queries (JPA handles the SQL)
│
├── service/
│   └── AuthService.java          ← Business logic (register + login rules)
│
├── controller/
│   └── AuthController.java       ← HTTP endpoints (routes requests to service)
│
├── exception/
│   ├── AuthExceptions.java       ← Custom exception types
│   └── GlobalExceptionHandler.java ← Converts exceptions to HTTP error responses
│
└── config/
    └── SecurityConfig.java       ← Security rules + BCrypt bean
```

### Request flow

```
HTTP Request
    ↓
AuthController         (validates input, routes to service)
    ↓
AuthService            (applies business rules)
    ↓
UserRepository         (reads/writes database)
    ↓
H2 Database
```

---

## Running tests

```bash
mvn test
```

---