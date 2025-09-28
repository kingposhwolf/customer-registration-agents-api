FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy gradle files
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Copy source code
COPY src src

# Build the application
RUN chmod +x gradlew && ./gradlew build -x test

# Create final image
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy the built jar file
COPY --from=0 /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 5001

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]