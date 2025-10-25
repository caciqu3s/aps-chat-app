package com.aps.chatapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Schema(description = "Chat message entity")
public class Message {

    @Id
    @Schema(description = "Unique identifier for the message", example = "64a1b2c3d4e5f6789abcdef0")
    private String id;

    @Schema(description = "Message author username", example = "john_doe")
    private String author;

    @Schema(description = "Message content", example = "Hello, this is a test message!")
    private String content;

    @Schema(description = "Message timestamp", example = "2023-10-24T15:30:45")
    private LocalDateTime timestamp;

    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
