# Product Service

A RESTful product microservice built with Spring Boot 3.

## Getting Started

```bash
mvn spring-boot:run
```

## Endpoints

| Method | Endpoint      | Description           |
|--------|---------------|-----------------------|
| GET    | /products     | List all products     |
| GET    | /products/{id}| Get product by ID    |
| POST   | /products     | Create a new product  |
| GET    | /health       | Service health check |

## Setup Instructions

### Prerequisites

- Java: JDK 17 or higher
- Maven: Apache Maven 3.8+

### Building the Application

To build the application, run the following command in the project root directory:

```bash
./mvnw clean package
```

Or on Windows:

```bash
mvnw.cmd clean package
```

### Running the Application

After building, run the application using:

```bash
./mvnw spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on port 8080.

### Running with Maven Wrapper (No Maven Installed)

If you don't have Maven installed, you can use the Maven wrapper included in the project:

```bash
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

## Request/Response Examples

#### GET /products

Response:
```json
[
  {
    "id": 1,
    "name": "Sample Product",
    "price": 29.99
  }
]
```

#### GET /products/{id}

Response:
```json
{
  "id": 1,
  "name": "Sample Product",
  "price": 29.99
}
```

#### POST /products

Request:
```json
{
  "name": "New Product",
  "price": 49.99
}
```

Response:
```json
{
  "id": 2,
  "name": "New Product",
  "price": 49.99
}
```

### Validation Rules

- `name`: Must not be blank
- `price`: Must be a positive value

### Error Responses

Invalid requests will return appropriate HTTP status codes with error details:

- `400 Bad Request` - Validation errors
- `404 Not Found` - Product not found
- `500 Internal Server Error` - Server errors

## Testing

Run the tests using:

```bash
./mvnw test
```

## License

This project is for demonstration purposes.
