# Multi-stage build for Spring Boot application
# Stage 1: Build the application
FROM gradle:8.4-jdk17 AS build
WORKDIR /app
# Copy gradle files first for better caching
COPY build.gradle settings.gradle gradle.properties ./
COPY gradle ./gradle
# Download dependencies (cached if no changes in gradle files)
RUN gradle dependencies --no-daemon
# Copy source code
COPY src ./src
# Build the application
RUN gradle bootJar --no-daemon
# Stage 2: Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
# Create a non-root user
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring
# Copy the JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar
# Expose the application port
EXPOSE 8080
# Set JVM options for container environment
ENV JAVA_OPTS="-Xmx512m -Xms256m"
# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
