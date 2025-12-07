# Book-Author REST API

Learning project for Spring Boot Web.

## Functionality
- REST API for managing books and authors
- CRUD operations for both entities
- One-to-many relationship (Author → Books)

## Tech Stack
- Java 17
- Spring Boot 3.5.7
- Spring Data JPA
- PostgreSQL
- H2 (testing)
- Lombok
- Maven
- SpringDoc OpenAPI (Swagger)

## Project Structure
- **Controllers** - REST endpoints for Books and Authors
- **Services** - Business logic layer
- **Repositories** - Data access with Spring Data JPA
- **Entities** - Database models
- **DTOs** - Data transfer objects for API requests/responses
- **Exception Handling** - Global exception handler

## API Endpoints

### Authors
- `POST /authors` - Create author
- `GET /authors` - List authors (paginated)
- `GET /authors/{id}` - Get author by ID
- `PUT /authors/{id}` - Full update author
- `PATCH /authors/{id}` - Partial update author
- `DELETE /authors/{id}` - Delete author

### Books
- `PUT /books/{isbn}` - Create/update book (idempotent)
- `GET /books` - List books (paginated)
- `GET /books/{isbn}` - Get book by ISBN
- `PATCH /books/{isbn}` - Partial update book
- `DELETE /books/{isbn}` - Delete book
