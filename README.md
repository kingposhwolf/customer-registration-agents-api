# Customer Registration Agents API

A production-ready RESTful service for managing customer registrations performed by designated agents. The service is secured with stateless JWT authentication and supports token blacklisting for logout and compromised-token scenarios. It is built with Java 21, Spring (Boot/MVC), Jakarta imports, and Lombok, and is containerized with Docker.

## Features

- JWT-based authentication and authorization
  - Token generation, username extraction, expiration checks
  - Token validation against the authenticated user
  - Token blacklisting (e.g., on logout) backed by an in-memory cache
- Layered architecture with controllers, services, repositories, DTOs, and filters
- Centralized exception handling and reusable annotations/utilities
- Gradle build with wrapper for reproducible builds
- Dockerfile for containerized deployment
- GitHub Actions workflow for CI/CD
- Optional static analysis via bugspot

## Tech Stack

- Language: Java 21
- Frameworks: Spring (Boot/MVC), Jakarta imports
- Security: JWT with token blacklist
- Caching: Caffeine (for token blacklisting)
- Build: Gradle
- Containerization: Docker
- Quality: GitHub Actions CI/CD, Sonar configuration
- Utilities: Lombok

## Project Structure

- controllers: REST endpoints
- services: business logic (includes JWT token handling and blacklisting)
- repositories: data access layer
- dtos: request/response payloads
- filters: request filters (e.g., authentication filter)
- config: application configuration (security, cache, etc.)
- exceptions: global and domain-specific error handling
- util and common: shared helpers and constants
- resources: application properties and environment-specific overrides

## Getting Started

### Prerequisites

- Java 21
- Docker (optional, for containerized run)
- No need to install Gradle—use the included wrapper

### Configuration

Create or update an environment-specific properties file (e.g., `application-dev.properties`) or use environment variables. Common properties include:

```properties
# Server
server.port=8080
spring.profiles.active=dev

# Database (example; adjust to your DB)
spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DB_NAME>
spring.datasource.username=<DB_USERNAME>
spring.datasource.password=<DB_PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=validate

# JWT
security.jwt.secret=<YOUR_SECURE_RANDOM_SECRET>
security.jwt.expiration-seconds=3600

# Token blacklist (in-memory via Caffeine)
security.jwt.blacklist.enabled=true
security.jwt.blacklist.ttl-seconds=3600
security.jwt.blacklist.maximum-size=10000

# Logging
logging.level.root=INFO
```


Replace placeholders with your values. Keep secrets out of source control.

### Build and Run (Local)

- Run with auto-reload during development:
```shell script
./gradlew clean bootRun
```

- Run tests:
```shell script
./gradlew test
```

- Produce an executable JAR:
```shell script
./gradlew clean build
  java -jar build/libs/customer-registration-agents-api-<version>.jar --spring.profiles.active=dev
```


### Docker

Build and run the image:

```shell script
# Build image
docker build -t <your-org>/customer-registration-agents-api:<tag> .

# Run container (example)
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e SECURITY_JWT_SECRET=<YOUR_SECURE_RANDOM_SECRET> \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<HOST>:<PORT>/<DB_NAME> \
  -e SPRING_DATASOURCE_USERNAME=<DB_USERNAME> \
  -e SPRING_DATASOURCE_PASSWORD=<DB_PASSWORD> \
  <your-org>/customer-registration-agents-api:<tag>
```


## Authentication Overview

- Issue JWT on successful authentication.
- Include the token on subsequent requests:
```
Authorization: Bearer <JWT_TOKEN>
```

- Tokens are verified for signature, subject (username), and expiration.
- Blacklisted tokens are rejected (e.g., after logout), leveraging an in-memory cache to track revoked tokens.

Note: Replace any example endpoints below with your actual routes configured in the application:
- POST /api/v1/auth/login → returns access token
- POST /api/v1/auth/logout → blacklists token
- Protected endpoints require Authorization: Bearer <JWT_TOKEN>

## CI/CD and Quality

- GitHub Actions workflow runs build and tests on pushes/PRs.
- Optional static code analysis configured via `sonar-project.properties`. Integrate with your Sonar server as needed.

## Development Tips

- Use the dev profile for local work: `--spring.profiles.active=dev`
- Lombok must be enabled in your IDE.
- Favor DTOs for request/response payloads; keep entities isolated in the persistence layer.
- Centralize exception handling and avoid leaking internal details in API responses.


### Postman Collection
For convenience, a Postman collection is included to help you explore and test the available API endpoints quickly.

- [Download Postman Collection](./postman/CustomerRegistrationAPI.postman_collection.json)  
  *(import this file into Postman to get started)*



## Contributing

- Fork the repository and create feature branches from main.
- Add/adjust tests for your changes.
- Ensure `./gradlew build` passes locally before opening a PR.
- Keep public API changes documented.

## License

Specify your license here (e.g., MIT, Apache-2.0). Include the full text in a LICENSE file.

## Contact

- Maintainer: Julius Mushi
- Email: juliusstephen1@gmail.com

---