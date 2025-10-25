package com.aps.chatapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testMessageCreation() {
        Message message = new Message("user1", "Hello World");

        assertEquals("user1", message.getAuthor());
        assertEquals("Hello World", message.getContent());
        assertNotNull(message.getTimestamp());
    }
    
    @Test
    void testMessageDefaultConstructor() {
        Message message = new Message();

        assertNotNull(message.getTimestamp());
    }
    
    @Test
    void testSettersAndGetters() {
        Message message = new Message();
        message.setId("456");
        message.setAuthor("user2");
        message.setContent("Test message");

        assertEquals("456", message.getId());
        assertEquals("user2", message.getAuthor());
        assertEquals("Test message", message.getContent());
    }
}
