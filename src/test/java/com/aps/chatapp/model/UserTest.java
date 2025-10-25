package com.aps.chatapp.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User("testuser", "password");

        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());
    }
    
    @Test
    void testUserWithRoles() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        
        User user = new User("testuser", "password", roles);

        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains("ROLE_USER"));
        assertTrue(user.getRoles().contains("ROLE_ADMIN"));
    }
    
    @Test
    void testSettersAndGetters() {
        User user = new User();
        user.setId("123");
        user.setUsername("newuser");
        user.setPassword("newpass");

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);

        assertEquals("123", user.getId());
        assertEquals("newuser", user.getUsername());
        assertEquals("newpass", user.getPassword());
        assertEquals(1, user.getRoles().size());
    }
}
