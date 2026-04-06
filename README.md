# Product Service

![CI](https://github.com/redkiros81294/product-service/actions/workflows/ci.yml/badge.svg)

A RESTful product microservice built with Spring Boot 3.

## Swagger UI

Access the API documentation at: `http://localhost:8080/swagger-ui.html`

## API Endpoints

| Method | Endpoint                | Description           | Status Code |
|--------|-------------------------|-----------------------|-------------|
| GET    | /api/v1/products       | List all products     | 200 OK      |
| GET    | /api/v1/products/{id}   | Get product by ID     | 200 / 404   |
| POST   | /api/v1/products       | Create new product    | 201 Created |
| PUT    | /api/v1/products/{id}  | Update product        | 200 / 404   |
| DELETE | /api/v1/products/{id}  | Delete product        | 204 / 404   |
| GET    | /health                | Service health check  | 200 OK      |

## Getting Started

```bash
./mvnw spring-boot:run
```

The application will start on port 8080.

## Prerequisites

- Java: JDK 17 or higher
- Maven: Apache Maven 3.8+

## Building

```bash
./mvnw clean package
```

## Testing

```bash
./mvnw test
```

## API Examples

### GET /api/v1/products

Response:
```json
[
  {
    "id": 1,
    "name": "Laptop",
    "price": 999.99,
    "stockQty": 10,
    "category": "Electronics"
  }
]
```

### POST /api/v1/products

Request:
```json
{
  "name": "New Product",
  "price": 29.99,
  "stockQty": 100,
  "category": "Electronics"
}
```

Response: `201 Created` with Location header

### Validation Errors

- Blank name → `400 Bad Request` with ProblemDetail
- Negative price → `400 Bad Request`

### Not Found Errors

- Unknown ID → `404 Not Found` with ProblemDetail

## License

This project is for demonstration purposes.
