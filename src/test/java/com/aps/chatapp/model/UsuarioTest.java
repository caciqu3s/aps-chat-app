package com.aps.chatapp.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    
    @Test
    void testUsuarioCreation() {
        Usuario usuario = new Usuario("testuser", "password");
        
        assertEquals("testuser", usuario.getUsername());
        assertEquals("password", usuario.getPassword());
        assertNotNull(usuario.getRoles());
        assertTrue(usuario.getRoles().isEmpty());
    }
    
    @Test
    void testUsuarioWithRoles() {
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        
        Usuario usuario = new Usuario("testuser", "password", roles);
        
        assertEquals("testuser", usuario.getUsername());
        assertEquals("password", usuario.getPassword());
        assertEquals(2, usuario.getRoles().size());
        assertTrue(usuario.getRoles().contains("ROLE_USER"));
        assertTrue(usuario.getRoles().contains("ROLE_ADMIN"));
    }
    
    @Test
    void testSettersAndGetters() {
        Usuario usuario = new Usuario();
        usuario.setId("123");
        usuario.setUsername("newuser");
        usuario.setPassword("newpass");
        
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        usuario.setRoles(roles);
        
        assertEquals("123", usuario.getId());
        assertEquals("newuser", usuario.getUsername());
        assertEquals("newpass", usuario.getPassword());
        assertEquals(1, usuario.getRoles().size());
    }
}
