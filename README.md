# InfinityCart
This is an robust E-Commerce application developed using Spring framework, Spring Boot, Maven, Hibernate, Spring Security And Redis

## Features
- User authentication and Autherization using Spring Security.
- Product catalog with CRUD operations.
- Shopping Cart functionality.
- Order processing and management.
- Resful API for integration with frontend application.
- Redis caching improved performance.

## Prerequisites
- Java 8 or later.
- Maven.
- MySql.
- Redis.
- Spring Security.

## Configuration
- Adjust application properties in 'src/main/resources/application.properties' for database connection, Redis Connection.
- customize security settings in 'src/main/java/com/example/config/SecurityConfig.java'.
- Configure JWT properties in 'application.properties' for token expiration and secret key.

## Database
- This application uses hibernate and JPA for database operations.
- Configure your database connection in 'application.properties'.


## Security
- User authentication and autherization are handles by Spring Security.
- Also the authentication and autherization are handled by Jwt tokens.
- Customize security properties in 'application.prperties'.
