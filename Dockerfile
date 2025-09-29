# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory
WORKDIR /app

# Copy gradle files
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Copy source code
COPY src src

# Build the application
RUN chmod +x gradlew && ./gradlew build -x test

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built jar file from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Create a non-root user to run the application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port
EXPOSE 5001

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:5001/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]