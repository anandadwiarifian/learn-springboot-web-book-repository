# Book-Author REST API

Learning project for Spring Boot Web.

## Functionality
- REST API for managing books and authors
- CRUD operations for both entities
- One-to-many relationship (Author → Books)
- Basic Authentication with role-based access control

## Tech Stack
- Java 17
- Spring Boot 3.5.7
- Spring Data JPA
- Spring Security (Basic Auth)
- PostgreSQL
- H2 (testing)
- Lombok
- Maven
- SpringDoc OpenAPI (Swagger)

## Prerequisites

### Java 17
This project requires Java 17 or higher.

**Install using SDKMAN (recommended):**
```bash
# Install SDKMAN
curl -s "https://get.sdkman.io" | bash

# Install Java 17
sdk install java 17.0.17

# Verify installation
java -version
```

**Or download directly from:**
- [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17)
- [OpenJDK](https://openjdk.org/projects/jdk/17/)

### Docker & PostgreSQL
This project uses PostgreSQL as the database. Run it using Docker:
- To run PostgreSQL container: 
```bash
docker run -d \
  --name postgres-dev \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=yourpassword \
  -p 5432:5432 \
  postgres:16-alpine
```

- To start the container (if already created):
```bash
docker start postgres-dev
```

## Project Structure
- **Controllers** - REST endpoints for Books and Authors
- **Services** - Business logic layer
- **Repositories** - Data access with Spring Data JPA
- **Entities** - Database models
- **DTOs** - Data transfer objects for API requests/responses
- **Exception Handling** - Global exception handler
- **Security** - Basic authentication configuration

## Configuration

### Environment Variables

The application can be configured using environment variables (recommended for production) or `application.properties` (for development).

| Environment Variable         | Property Name                | Description             | Default                                     |
| ---------------------------- | ---------------------------- | ----------------------- | ------------------------------------------- |
| `SPRING_DATASOURCE_URL`      | `spring.datasource.url`      | PostgreSQL JDBC URL     | `jdbc:postgresql://localhost:5432/postgres` |
| `SPRING_DATASOURCE_USERNAME` | `spring.datasource.username` | Database username       | `postgres`                                  |
| `SPRING_DATASOURCE_PASSWORD` | `spring.datasource.password` | Database password       | `yourpassword`                              |
| `SECURITY_USER_PASSWORD`     | `security.user.password`     | Password for user role  | `user-password`                             |
| `SECURITY_ADMIN_PASSWORD`    | `security.admin.password`    | Password for admin role | `admin-password`                            |

**Note:** Environment variables automatically override properties defined in `application.properties`.

### Example: Setting Environment Variables

```bash
# Database configuration
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mydb
export SPRING_DATASOURCE_USERNAME=dbuser
export SPRING_DATASOURCE_PASSWORD=dbpass

# Security credentials
export SECURITY_USER_PASSWORD=secure-user-pass
export SECURITY_ADMIN_PASSWORD=secure-admin-pass
```

## Security

### Authentication
This API uses HTTP Basic Authentication. Include credentials in the `Authorization` header:

```
Authorization: Basic base64(username:password)
```

### Users and Roles
- **User** (username: `user`) - Can view and modify books and authors
- **Admin** (username: `admin`) - Full access including DELETE operations

### Access Control
- **Public** - `/healthcheck` (no authentication required)
- **USER or ADMIN** - All GET, POST, PUT, PATCH operations on `/authors/**` and `/books/**`
- **ADMIN only** - All DELETE operations

## API Endpoints

### Health
- `GET /healthcheck` - Health check endpoint (public, no auth required)

### Authors
- `POST /authors` - Create author (requires: USER or ADMIN)
- `GET /authors` - List authors (paginated) (requires: USER or ADMIN)
- `GET /authors/{id}` - Get author by ID (requires: USER or ADMIN)
- `PUT /authors/{id}` - Full update author (requires: USER or ADMIN)
- `PATCH /authors/{id}` - Partial update author (requires: USER or ADMIN)
- `DELETE /authors/{id}` - Delete author (requires: ADMIN)

### Books
- `PUT /books/{isbn}` - Create/update book (idempotent) (requires: USER or ADMIN)
- `GET /books` - List books (paginated) (requires: USER or ADMIN)
- `GET /books/{isbn}` - Get book by ISBN (requires: USER or ADMIN)
- `PATCH /books/{isbn}` - Partial update book (requires: USER or ADMIN)
- `DELETE /books/{isbn}` - Delete book (requires: ADMIN)

## Running the Application

### Development (using defaults from application.properties)
```bash
./mvnw spring-boot:run
```

### Production (using environment variables)
```bash
# Set environment variables
export SPRING_DATASOURCE_URL=jdbc:postgresql://prod-db:5432/bookdb
export SPRING_DATASOURCE_USERNAME=prod_user
export SPRING_DATASOURCE_PASSWORD=prod_secret
export SECURITY_USER_PASSWORD=strong-user-password
export SECURITY_ADMIN_PASSWORD=strong-admin-password

# Run the application
./mvnw spring-boot:run
```

### Making Authenticated Requests

Using curl:
```bash
# Public endpoint (no auth)
curl http://localhost:8080/healthcheck

# With user credentials
curl -u user:user-password http://localhost:8080/authors

# With admin credentials
curl -u admin:admin-password -X DELETE http://localhost:8080/authors/1
```

## API Testing with Postman

Postman collection and environment files are available in the `/postman` directory:

- **Collection**: `postman/book-author-restapi.postman_collection.json`
- **Environment**: `postman/book-author-restapi.postman_environment.json`

### Environment Variables

The environment file includes:

| Variable              | Default Value    | Description                 |
| --------------------- | ---------------- | --------------------------- |
| `BASE_URL`            | `localhost:8080` | API base URL                |
| `BASIC_AUTH_USERNAME` | `admin`          | Username for authentication |
| `BASIC_AUTH_PASSWORD` | `admin-password` | Password for authentication |

**Note**: Update the credentials in the environment to match your configuration (user/user-password or admin/admin-password).