[![CodeFactor](https://www.codefactor.io/repository/github/dmitriy-iliyov/specialist-backend/badge)](https://www.codefactor.io/repository/github/dmitriy-iliyov/specialist-backend)
[![codecov](https://codecov.io/github/dmitriy-iliyov/specialist-backend/graph/badge.svg?token=UDCPIBRJQJ)](https://codecov.io/github/dmitriy-iliyov/specialist-backend)
[![CI](https://github.com/dmitriy-iliyov/specialist-backend/actions/workflows/ci.yml/badge.svg)](https://github.com/dmitriy-iliyov/specialist-backend/actions/workflows/ci.yml)

![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-6DB33F?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-336791?logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-7+-DC382D?logo=redis)
![Lua](https://img.shields.io/badge/Lua-Redis%20Scripts-2C2D72?logo=lua)
![Prometheus](https://img.shields.io/badge/Prometheus-Metrics-E6522C?logo=prometheus)
![Grafana](https://img.shields.io/badge/Grafana-Dashboards-F46800?logo=grafana)

## Overview

`specialist-backend` is a backend service for a platform designed to find specialists and manage appointments. It provides a RESTful API for managing user and specialist accounts, configuring their schedules, and handling the appointment booking process.

## Key Features

- User and specialist account management.
- Advanced specialist search with filtering by various criteria.
- Rating and review system for specialists, allowing users to leave feedback and comments.
- Booking, cancellation, and rescheduling of appointments.
- Personal schedule management for specialists.
- RESTful API with interactive documentation powered by OpenAPI (Swagger).

## Architecture

The project is implemented using a classic **layered architecture** (Controller-Service-Repository), which ensures a clear separation of concerns and high maintainability.

- **Controller Layer**: Handles HTTP requests, validates input, and invokes the service layer.
- **Service Layer**: Contains the core business logic of the application.
- **Repository Layer**: Responsible for interacting with the database (PostgreSQL) via Spring Data JPA.

### Modules

- **auth-module**: Manages user authentication and authorization. It handles JWT generation, refresh token logic, service accounts management, and role/authority management.
- **core-module**: Provides core infrastructure used across the application, such as common configurations, shared exception handling.
- **message-module**: Implements notification and messaging logic, including appointment reminders and system-generated messages.
- **notification-module**: A dedicated module for handling real-time event-driven notifications (e.g., appointment cancellations).
- **profile-module**: Manages user and specialist profiles, including personal data, contact information, and other profile-related details.
- **schedule-module**: Handles all scheduling logic and appointment planning. It manages specialist availability, appointment intervals, and the complete booking lifecycle.
- **specialist-directory-module**: Responsible for the public directory of specialists. This includes advanced search, filtering, specialist ratings, reviews, and bookmarking functionality.

## Non-functional Requirements

### Authentication & Security

The application implements a multi-layered security model to protect user data and system resources.

#### JWT-Based Authentication
The primary authentication mechanism is built on JSON Web Tokens (JWT), ensuring a stateless approach and good scalability.
- **Access & Refresh Tokens**: After a successful login, the client receives a short-lived `access token` for API access and a long-lived `refresh token` to renew the session.
- **Refresh Token Rotation**: To enhance security, a rotation mechanism is used. When an `access token` expires, the client uses the `refresh token` to obtain a new pair of tokens. The old `refresh token` is immediately invalidated, preventing its reuse and reducing risks in case of compromise.

#### Social Login (OAuth 2.0)
The platform also supports authentication through third-party providers (Google, Facebook), simplifying the registration and login process for users.

#### Security Measures
- **RBAC**: The system utilizes a flexible RBAC model where roles are composed of fine-grained permissions (authorities). This allows for granular control over specific actions and resources, rather than relying on simple role checks. Access to endpoints is strictly controlled based on the permissions assigned to a user's role.
- **CSRF Protection**: A robust stateless CSRF protection mechanism is in place. The server generates a CSRF token and stores it in a secure, `HttpOnly` cookie, making it inaccessible to client-side scripts. The frontend application retrieves the token by making a dedicated API call to a specific endpoint. For every subsequent state-changing request (POST, PUT, DELETE), the client must include this token in a custom `X-XSRF-TOKEN` header for server-side validation.
- **XSS Protection**: The application uses a custom filter that automatically sanitizes incoming request bodies to neutralize malicious scripts, preventing XSS attacks.
- **Rate Limiting**: To protect against brute-force and DoS attacks, sensitive endpoints (like authentication) are rate-limited based on the client's IP address.

### Caching & Performance

- **Redis** is used for caching frequently accessed data, significantly reducing the load on PostgreSQL.
- **Lua scripts** are used to perform atomic operations in Redis, ensuring consistency and high performance for complex scenarios (e.g., managing time slots).

### Observability

- The application exports metrics in **Prometheus** format for monitoring health and performance.
- **Grafana** is used to visualize metrics and create dashboards.
- The metrics endpoint is available at: `/api/system/actuator/prometheus`.

## API Documentation

Once the application is running, interactive API documentation is available at:
**<https://localhost:8443/swagger-ui.html>**

## Run

The project uses Docker to run external dependencies (database, cache).

### Prerequisites

- JDK 21+
- Maven 3.8+
- Docker and Docker Compose

### Instructions

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/dmitriy-iliyov/specialist-backend.git
    cd specialist-backend
    ```

2.  **Start the environment:**
    Run PostgreSQL and Redis using Docker Compose.
    ```bash
    docker-compose up -d
    ```

3.  **Configure environment variables:**
    The application requires environment variables to be set for database, Redis, and SSL configuration. You can export them in your shell or use a `.env` file in the project root.

    Example variables:
    ```properties
    DATABASE_URL=jdbc:postgresql://localhost:5432/specialist
    DATABASE_USERNAME=user
    DATABASE_PASSWORD=password
    
    REDIS_HOST=localhost
    REDIS_PORT=6379
    
    SSL_KEY_STORE=path/to/your/keystore.p12
    SSL_KEY_STORE_PASSWORD=your_password
    SSL_KEY_PASSWORD=your_key_password
    
    AZURE_BLOB_AVATARS_CONTAINER_NAME=container_name
    AZURE_BLOB_STORAGE_CONNECTION_STRING=connection_string
    
    AUTH_TOKEN_SECRET=access_token_secret
    
    COMPANY_MAIL=example.com@gmail.com
    COMPANY_MAIL_PASS=password
    ```

4.  **Build the project:**
    ```bash
    mvn clean install
    ```

5.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    The application will be available at `https://localhost:8443`.

## Testing

To run all tests (unit and integration), execute the command:
```bash
mvn test
```
