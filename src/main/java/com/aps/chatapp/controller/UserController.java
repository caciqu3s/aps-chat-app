package com.aps.chatapp.controller;

import com.aps.chatapp.model.User;
import com.aps.chatapp.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User registration and management endpoints")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(
        summary = "Register new user",
        description = "Register a new user in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "User registered successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid user data or username already exists"),
        @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Username already exists"));
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton("USER"));

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRoles()));
    }

    @GetMapping
    @Operation(
        summary = "Get all users",
        description = "Retrieve all registered users (Admin only)"
    )
    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Users retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class)
            )
        ),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required")
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRoles()))
                .toList();
        return ResponseEntity.ok(userResponses);
    }
}

@Schema(description = "User registration request")
class UserRegistrationRequest {
    @Schema(description = "Username for the new account", example = "john_doe", required = true)
    private String username;

    @Schema(description = "Password for the new account", example = "password123", required = true)
    private String password;

    public UserRegistrationRequest() {}

    public UserRegistrationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

@Schema(description = "User information response")
class UserResponse {
    @Schema(description = "User ID", example = "64a1b2c3d4e5f6789abcdef0")
    private String id;

    @Schema(description = "Username", example = "john_doe")
    private String username;

    @Schema(description = "User roles", example = "[\"USER\"]")
    private java.util.Set<String> roles;

    public UserResponse() {}

    public UserResponse(String id, String username, java.util.Set<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public java.util.Set<String> getRoles() { return roles; }
    public void setRoles(java.util.Set<String> roles) { this.roles = roles; }
}

@Schema(description = "Error response")
class ErrorResponse {
    @Schema(description = "Error message", example = "Username already exists")
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
