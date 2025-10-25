package com.aps.chatapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Schema(description = "User entity for authentication and chat participation")
public class User {

    @Id
    @Schema(description = "Unique identifier for the user", example = "64a1b2c3d4e5f6789abcdef0")
    private String id;
    
    @Indexed(unique = true)
    @Schema(description = "Unique username for authentication", example = "john_doe")
    private String username;
    
    @Schema(description = "Encrypted password", example = "$2a$10$...")
    private String password;
    
    @Schema(description = "User roles for authorization", example = "[\"USER\", \"ADMIN\"]")
    private Set<String> roles = new HashSet<>();
    
    public User() {
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public User(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
