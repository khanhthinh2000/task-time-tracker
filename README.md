# Sprint Task & Time Tracker API

A lightweight **Java / Spring Boot / PostgreSQL** backend for tracking:

- Projects
- Tasks under each project
- Time entries logged against tasks

This project demonstrates real-world backend development skills using enterprise technologies such as Java, Spring, Hibernate, REST APIs, PostgreSQL, JUnit, and Maven.

---

## 1. Overview

The API provides:

- CRUD endpoints for **Projects**
- CRUD endpoints for **Tasks**
- Time logging via **Time Entries**
- PostgreSQL persistence using **Spring Data JPA + Hibernate**
- Automated tests using **JUnit + Spring Boot Test + MockMvc**

This is a backend-only application tested using **Postman** and inspected with **pgAdmin**.

---

## 2. Tech Stack

### Language & Runtime
- Java 21

### Frameworks
- Spring Boot 3 (Web, Data JPA)
- Hibernate
- Jackson (JSON serialization)

### Database
- PostgreSQL
- pgAdmin for visual management

### Build & Testing
- Maven (via `mvnw`)
- JUnit 5
- Spring Boot Test
- MockMvc
- AssertJ

---

## 3. Project Structure

`com.novinsdev.tasktimetracker`

### project package
- `Project` – Entity mapped to `projects`
- `ProjectRepository` – JPA repository
- `ProjectController` – REST endpoints `/api/projects`
- `ProjectRequest` – DTO for creating projects

### task package
- `Task` – Entity mapped to `tasks`
- `TaskStatus` – Enum (TODO, IN_PROGRESS, DONE)
- `TaskRepository` – Query tasks by project
- `TaskController` – REST endpoints `/api/tasks`
- `TaskRequest` – DTO for creating tasks

### timeentry package
- `TimeEntry` – Entity mapped to `time_entries`
- `TimeEntryRepository` – Query by task & date range
- `TimeEntryController` – REST endpoints `/api/time-entries`
- `TimeEntryRequest` – DTO for logging time

### tests
- `ProjectRepositoryTest` – JPA persistence test
- `ProjectControllerTest` – REST API test with MockMvc
- Tests clean data in FK-safe order: `time_entries → tasks → projects`

---

## 4. Database Setup

Create PostgreSQL database:

```sql
CREATE DATABASE task_time_tracker;
```

Create user:

```sql
CREATE USER task_user WITH PASSWORD 'your_password_here';
GRANT ALL PRIVILEGES ON DATABASE task_time_tracker TO task_user;
```

Application config:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_time_tracker
spring.datasource.username=task_user
spring.datasource.password=your_password_here

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 5. Running the Application

Start the backend:

```bash
./mvnw spring-boot:run
```

Default server:

```
http://localhost:8080
```

---

## 6. API Endpoints & Examples

---

### 6.1 Projects

#### Create Project  
POST `/api/projects`

Request:

```json
{
  "name": "Backend Revamp",
  "description": "Refactor legacy services"
}
```

Response:

```json
{
  "id": 1,
  "name": "Backend Revamp",
  "description": "Refactor legacy services"
}
```

---

#### Get All Projects  
GET `/api/projects`

Example response:

```json
[
  {
    "id": 1,
    "name": "Backend Revamp",
    "description": "Refactor legacy services"
  }
]
```

---

#### Get Project by ID  
GET `/api/projects/1`

---

### 6.2 Tasks

#### Create Task  
POST `/api/tasks`

Request:

```json
{
  "title": "Implement time API",
  "status": "TODO",
  "projectId": 1
}
```

Response:

```json
{
  "id": 1,
  "title": "Implement time API",
  "status": "TODO",
  "project": {
    "id": 1,
    "name": "Backend Revamp",
    "description": "Refactor legacy services"
  }
}
```

---

#### Get All Tasks  
GET `/api/tasks`

#### Get Tasks by Project  
GET `/api/tasks?projectId=1`

---

### 6.3 Time Entries

#### Log Time  
POST `/api/time-entries`

Request:

```json
{
  "taskId": 1,
  "workDate": "2025-11-21",
  "hours": 2.5,
  "note": "Implemented API"
}
```

Response:

```json
{
  "id": 1,
  "task": { ... },
  "workDate": "2025-11-21",
  "hours": 2.5,
  "note": "Implemented API"
}
```

---

#### Get Time Entries for Task  
GET `/api/time-entries?taskId=1`

#### Get Time Entries with Date Range  
GET `/api/time-entries?taskId=1&startDate=2025-11-01&endDate=2025-11-30`

---

## 7. Testing

Run tests:

```bash
./mvnw test
```

Current tests demonstrate:

- JPA repository behavior
- REST controller behavior
- MockMvc request/response validation
- Proper DB cleanup to avoid FK issues

---

## 8. What This Project Demonstrates

- REST API design using Spring Boot  
- Entity relationships (One-to-Many, Many-to-One)  
- Spring Data JPA & Hibernate ORM  
- PostgreSQL relational modeling  
- DTO + Controller patterns  
- Handling lazy-loading and JSON serialization  
- Writing meaningful integration tests  
- Using Maven and Spring Boot Test frameworks  

This project provides a strong foundation to discuss during an interview, showing hands-on experience with production-style backend development using Java and Spring Boot.

---

## License

This project is shared publicly to showcase learning, craftsmanship, and technical capabilities.  
While the repository is accessible, the source code and all associated materials remain the intellectual property of the author.

To maintain the integrity of this work, please refrain from reusing the code directly in other projects or redistributing it in any form.  
If you wish to reference the project, linking to the repository is warmly appreciated.

Thank you for respecting the spirit in which this work is made available.
