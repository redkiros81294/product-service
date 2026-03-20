# product-service
![CI](https://github.com/<redkiros81294>/product-service/actions/workflows/ci.yml/badge.svg)
![CI](https://github.com/<yeab-samuel>/product-service/actions/workflows/ci.yml/badge.svg)

A RESTful product microservice built with Spring Boot 3.
## Getting Started
mvn spring-boot:run
## Endpoints
| Method | Path | Description |
|--------|------------------|------------------------|
| GET | /products | List all products |
| GET | /products/{id} | Get product by ID |
| POST | /products | Create a new product |
| GET | /health | Service health check |

## Setup Instructions

### Prerequisites

- Java: JDK 17 or higher
- Maven: Apache Maven 3.8+

### Building the Application

To build the application, run the following command in the project root directory:

./mvnw clean package

Or on Windows:

mvnw.cmd clean package

### Running the Application

After building, run the application using:

./mvnw spring-boot:run

Or on Windows:

mvnw.cmd spring-boot:run

The application will start on port 8080.

### Running with Maven Wrapper (No Maven Installed)

If you don't have Maven installed, you can use the Maven wrapper included in the project:

./mvnw clean package -DskipTests
./mvnw spring-boot:run

## Available Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /products | Get all products |
| GET | /products/{id} | Get product by ID |
| POST | /products | Create a new product |
| GET | /health | Health check endpoint |

### Request/Response Examples

#### GET /products

Response:
[
{
"id": 1,
"name": "Sample Product",
"price": 29.99
}
]

#### GET /products/{id}

Response:
{
"id": 1,
"name": "Sample Product",
"price": 29.99
}

#### POST /products

Request:
{
"name": "New Product",
"price": 49.99
}

Response:
{
"id": 2,
"name": "New Product",
"price": 49.99
}

### Validation Rules

- name: Must not be blank
- price: Must be a positive value

### Error Responses

Invalid requests will return appropriate HTTP status codes with error details:

- 400 Bad Request - Validation errors
- 404 Not Found - Product not found
- 500 Internal Server Error - Server errors


## Testing

Run the tests using:

./mvnw test

## CI/CD

<!-- CI Badge: Add once pipeline is configured -->

## License

This project is for demonstration purposes.