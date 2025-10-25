package com.aps.chatapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MensagemTest {
    
    @Test
    void testMensagemCreation() {
        Mensagem mensagem = new Mensagem("user1", "Hello World");
        
        assertEquals("user1", mensagem.getAutor());
        assertEquals("Hello World", mensagem.getTexto());
        assertNotNull(mensagem.getTimestamp());
    }
    
    @Test
    void testMensagemDefaultConstructor() {
        Mensagem mensagem = new Mensagem();
        
        assertNotNull(mensagem.getTimestamp());
    }
    
    @Test
    void testSettersAndGetters() {
        Mensagem mensagem = new Mensagem();
        mensagem.setId("456");
        mensagem.setAutor("user2");
        mensagem.setTexto("Test message");
        
        assertEquals("456", mensagem.getId());
        assertEquals("user2", mensagem.getAutor());
        assertEquals("Test message", mensagem.getTexto());
    }
}
