# TaskOrbit Backend - Spring Boot REST API

Spring Boot 3.x REST API service for **TaskOrbit 3D Daily Planner**, backed by MongoDB.

## Features
- Complete CRUD operations for tasks
- Validation with `@NotBlank` on task title (returns structured 400 Bad Request)
- Custom exceptions and centralized `@RestControllerAdvice` error handling
- Task status toggling (`PENDING` <-> `COMPLETED`)
- Aggregate statistics endpoint (`GET /api/tasks/stats`)
- Automatic sorting by `dueDate` ascending
- CORS support for React frontend (`http://localhost:5173`)
- Database seeding with realistic tasks on first startup

## Tech Stack
- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data MongoDB**
- **Jakarta Validation**
- **Maven**

## REST Endpoints
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/tasks` | Create task |
| `GET` | `/api/tasks?status=ALL\|PENDING\|COMPLETED` | List tasks |
| `GET` | `/api/tasks/stats` | Aggregated stats |
| `GET` | `/api/tasks/{id}` | Get one task |
| `PUT` | `/api/tasks/{id}` | Update task |
| `PATCH` | `/api/tasks/{id}/status` | Toggle/update status |
| `DELETE` | `/api/tasks/{id}` | Delete task |

## Setup & Run
1. Ensure MongoDB is running on `localhost:27017`.
2. Build and run:
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```
   *By default, the server runs on port `8081` (configurable via `server.port` in `application.properties` or `PORT` environment variable).*
