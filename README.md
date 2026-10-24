# Task Manager API

A RESTful API built with **Spring Boot 4** and **Java 21** for managing tasks. It supports full CRUD operations, status filtering, case-insensitive title search, request validation via DTOs, centralized exception handling, and interactive **OpenAPI 3 (Swagger UI)** documentation.

---

## Features

- **CRUD Operations**: Create, read, update, and delete tasks.
- **Filtering & Search**: Filter tasks by completion status (`true`/`false`) or search tasks by title keyword (case-insensitive).
- **DTO Pattern & Validation**: Clean separation between database entities and API payloads using `TaskRequestDTO` and `TaskResponseDTO` with Jakarta Bean Validation (`@NotBlank`, `@Size`).
- **Global Exception Handling**: Standardized JSON error responses (`ErrorResponseDTO`) across the API using `@RestControllerAdvice` for resource-not-found (`404`), validation failures (`400`), malformed JSON (`400`), and unexpected server errors (`500`).
- **OpenAPI / Swagger Documentation**: Interactive API exploration and OpenAPI 3.0 specification powered by `springdoc-openapi`.
- **In-Memory H2 Database**: Pre-configured zero-setup in-memory database with H2 Web Console enabled.

---

## Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 4.1.2 (Spring Web MVC, Spring Data JPA, Spring Validation)
- **Database**: H2 In-Memory Database
- **API Documentation**: Springdoc OpenAPI (`springdoc-openapi-starter-webmvc-ui` 3.1.1)
- **Utilities**: Lombok
- **Build Tool**: Maven (`mvnw` wrapper included)

---

## Project Structure

```text
src/main/java/com/liyacommits/task_manager
├── config
│   └── OpenApiConfig.java             # OpenAPI 3 / Swagger metadata configuration
├── controller
│   └── TaskController.java            # REST endpoints with OpenAPI annotations
├── dto
│   ├── ErrorResponseDTO.java          # Standardized error response structure
│   ├── TaskRequestDTO.java            # Validated request payload for create/update
│   └── TaskResponseDTO.java           # Response payload returned to clients
├── entity
│   └── Task.java                      # JPA entity mapped to the 'tasks' table
├── exception
│   ├── GlobalExceptionHandler.java    # @RestControllerAdvice centralized error handler
│   └── ResourceNotFoundException.java # Custom 404 runtime exception
├── respository
│   └── TaskRepository.java            # Spring Data JPA repository
├── service
│   └── TaskService.java               # Business logic and DTO <-> Entity mapping
└── TaskManagerApplication.java        # Main Spring Boot entry point
```

---

## Getting Started

### Prerequisites

- **JDK 21** or higher installed
- (Optional) Maven installed — the project includes the Maven Wrapper (`./mvnw`)

### Running the Application

1. **Clone the repository and navigate to the project directory:**
   ```bash
   cd task_manager
   ```

2. **Start the application using the Maven wrapper:**
   ```bash
   ./mvnw spring-boot:run
   ```

The server will start at `http://localhost:8080`.

---

## API Documentation (OpenAPI / Swagger UI)

Once the application is running, you can access the interactive OpenAPI documentation and H2 database console:

| Resource | URL | Description |
| :--- | :--- | :--- |
| **Swagger UI** | `http://localhost:8080/swagger-ui.html` | Interactive UI to explore and test endpoints |
| **OpenAPI Spec (JSON)** | `http://localhost:8080/api-docs` | Raw OpenAPI 3.0 JSON definition |
| **H2 Database Console** | `http://localhost:8080/h2-console` | JDBC URL: `jdbc:h2:mem:taskdb`, User: `sa`, Password: `password` |

---

## API Endpoints Reference

Base URL: `/api/v1/tasks`

| Method | Endpoint | Description | Success Status |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/tasks` | Get all tasks | `200 OK` |
| `GET` | `/api/v1/tasks/{id}` | Get a task by ID | `200 OK` |
| `POST` | `/api/v1/tasks` | Create a new task | `201 Created` |
| `PUT` | `/api/v1/tasks/{id}` | Update an existing task by ID | `200 OK` |
| `DELETE` | `/api/v1/tasks/{id}` | Delete a task by ID | `204 No Content` |
| `GET` | `/api/v1/tasks/completed/{status}` | Get tasks by completion status (`true` or `false`) | `200 OK` |
| `GET` | `/api/v1/tasks/search?title={keyword}` | Search tasks by title keyword (case-insensitive) | `200 OK` |

### Data Transfer Objects (DTOs)

#### `TaskRequestDTO` (Request Body for `POST` & `PUT`)
```json
{
  "title": "Complete OpenAPI documentation",
  "description": "Add Swagger annotations, DTOs, and global exception handling to the API",
  "completed": false
}
```
*Validation Rules:*
- `title`: Required (`@NotBlank`), maximum 255 characters.
- `description`: Optional, maximum 1024 characters.
- `completed`: Optional boolean (defaults to `false` on creation if omitted).

#### `TaskResponseDTO` (Response Body)
```json
{
  "id": 1,
  "title": "Complete OpenAPI documentation",
  "description": "Add Swagger annotations, DTOs, and global exception handling to the API",
  "completed": false,
  "createdAt": "2026-10-24T19:15:00"
}
```

---

## Exception Handling & Error Responses

All exceptions are intercepted by `GlobalExceptionHandler` and return a consistent `ErrorResponseDTO` JSON payload.

### 1. Resource Not Found (`404 Not Found`)
Returned when a requested task ID does not exist (e.g., `GET /api/v1/tasks/99`).
```json
{
  "timestamp": "2026-10-24T19:15:00",
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: '99'",
  "path": "/api/v1/tasks/99"
}
```

### 2. Validation Failure (`400 Bad Request`)
Returned when `@Valid` fails on `TaskRequestDTO` (e.g., blank `title`).
```json
{
  "timestamp": "2026-10-24T19:15:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for one or more fields",
  "path": "/api/v1/tasks",
  "validationErrors": {
    "title": "Task title is required and cannot be blank"
  }
}
```

---

## Running Tests

To execute the unit and integration test suite:

```bash
./mvnw test
```
