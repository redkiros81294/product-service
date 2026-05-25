package com.ctbe.yared.productservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
    title = "Product Service API",
    version = "1.0.0",
    description = "RESTful Product Catalogue - Lab 3 (PostgreSQL + Flyway + JPA Relationships)"
))
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    // Seed data is now handled by Flyway migrations (V5__seed_categories.sql) + API calls.
    // The old in-memory seeding has been removed for Lab 3 (PostgreSQL + proper Category relationships).

}
