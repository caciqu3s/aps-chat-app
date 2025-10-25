# APS Chat App

Spring Boot application with MongoDB, WebSocket, Security, and JWT authentication.

## Features

- **MongoDB Integration**: Document-based storage for users and messages
- **JWT Authentication**: Stateless authentication using JSON Web Tokens
- **WebSocket Support**: Real-time messaging with STOMP protocol
- **Spring Security**: Secured endpoints with role-based access control

## Quick Start

Get the application running in minutes with Docker:

```bash
# Clone the repository (if not already done)
# cd /path/to/aps-chat-app

# Start the application and MongoDB with Docker Compose
docker-compose up -d

# View application logs
docker-compose logs -f chat-app

# The application will be available at http://localhost:8080
```

To stop the application:
```bash
docker-compose down
```

## Prerequisites

### For Docker (Recommended)
- Docker 20.10 or higher
- Docker Compose 2.0 or higher

### For Local Development
- Java 17 or higher
- Maven 3.6 or higher
- MongoDB 4.4 or higher

## Configuration

The application uses environment variables for sensitive configuration:

- `MONGODB_URI`: MongoDB connection string (default: `mongodb://localhost:27017/chatapp`)
- `JWT_SECRET`: Secret key for JWT token signing (default: provided in application.properties)

## Project Structure

```
src/main/java/com/aps/chatapp/
├── ChatAppApplication.java          # Main application class
├── config/
│   └── WebSocketConfig.java         # WebSocket configuration
├── controller/
│   ├── AuthController.java          # Authentication endpoints
│   └── WebSocketController.java     # WebSocket message handling
├── model/
│   ├── User.java                    # User entity with roles
│   └── Message.java                 # Message entity with timestamp
├── repository/
│   ├── UserRepository.java          # User data access
│   └── MessageRepository.java       # Message data access
└── security/
    ├── SecurityConfig.java          # Security configuration
    ├── JwtUtil.java                 # JWT token utilities
    ├── JwtRequestFilter.java        # JWT authentication filter
    └── CustomUserDetailsService.java # User details service
```

## API Endpoints

### Authentication

#### POST /api/auth/login
Authenticate user and receive JWT token.

**Request:**
```json
{
  "username": "user",
  "password": "password"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### WebSocket

#### Connect to WebSocket
- **Endpoint**: `/ws`
- **Protocol**: STOMP over SockJS

#### Send Message
- **Destination**: `/app/relatorio`
- **Broadcast**: `/topic/dashboard`

## Data Models

### User
```java
{
  "id": "string",
  "username": "string",
  "password": "string (encrypted)",
  "roles": ["USER", "ADMIN"]
}
```

### Message
```java
{
  "id": "string",
  "author": "string",
  "content": "string",
  "timestamp": "LocalDateTime"
}
```

## Security Configuration

- **Session Management**: Stateless (JWT-based)
- **Public Endpoints**: 
  - `/api/auth/**` - Authentication endpoints
  - `/ws/**` - WebSocket connections
- **Protected Endpoints**: All other endpoints require valid JWT token

## Building the Application

```bash
# Compile the application
mvn clean compile

# Run tests
mvn test

# Build the JAR package
mvn clean package
```

## Running the Application

### Using Docker Compose (Recommended)

The easiest way to run the application is using Docker Compose, which starts both the application and MongoDB:

```bash
# Start the application and MongoDB
docker-compose up -d

# View logs
docker-compose logs -f chat-app

# Stop the application
docker-compose down

# Stop and remove volumes (clean restart)
docker-compose down -v
```

The application will be available at `http://localhost:8080` and MongoDB at `localhost:27017`.

### Using Docker Only

```bash
# Build the Docker image
docker build -t aps-chat-app .

# Run MongoDB
docker run -d --name mongodb -p 27017:27017 mongo:7.0

# Run the application
docker run -d --name chat-app -p 8080:8080 \
  -e MONGODB_URI=mongodb://host.docker.internal:27017/chatapp \
  aps-chat-app
```

### Running Locally (Without Docker)

```bash
# Run with default configuration
java -jar target/chat-app-0.0.1-SNAPSHOT.jar

# Run with custom MongoDB URI
MONGODB_URI=mongodb://your-mongo-host:27017/chatapp java -jar target/chat-app-0.0.1-SNAPSHOT.jar

# Run with custom JWT secret
JWT_SECRET=your-secret-key java -jar target/chat-app-0.0.1-SNAPSHOT.jar
```

## Testing

The project includes comprehensive tests:

- **Unit Tests**: JwtUtil, User, Message
- **Integration Tests**: Application context loading
- **Total**: 11 tests covering core functionality

Run tests with:
```bash
mvn test
```

## Development

The application uses:
- Spring Boot 3.1.5
- Java 17
- MongoDB Driver 4.9.1
- JJWT 0.11.5
- Spring Security
- Spring WebSocket

## License

This project is part of the APS (Academic Programming Study) coursework.