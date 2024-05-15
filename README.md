# Spring Security JWT

REST API project developed in Java using Spring Boot 3, Spring Security 6 for JWT Authentication and Authorization, and H2 database.

## Features

- Token-based Authentication using JSON Web Token (JWT)
- Role-based authorization
- Encoding password with BCrypt algorithm

## Requirements

- Java 17
- Spring Boot 3.2.4
- Spring Security 6.2.3
- Apache Maven 3.8.6

## Run App

- Set the following environment variables: 'ADMIN_PASSWORD' and 'SECRET_KEY' (size >= 256 bits)
- Run the SpringSecurityJwtApplication class as Java Application

## Collection

[Project Collection](https://github.com/erebelo/spring-security-jwt/tree/main/collection)

## AWS Demo

[Spring Security JWT](https://jwt.erebelo.com/spring-security-jwt/swagger-ui/index.html)

## AWS Deployment

Follow the [AWS Docker](https://github.com/erebelo/aws-docker/tree/main) guide to deploy a **Java App** instance and how to set up **Nginx** as a reverse proxy with a valid Wildcard SSL/TLS certificate.
