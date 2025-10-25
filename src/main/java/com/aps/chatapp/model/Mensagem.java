package com.aps.chatapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "mensagens")
public class Mensagem {
    
    @Id
    private String id;
    
    private String autor;
    
    private String texto;
    
    private LocalDateTime timestamp;
    
    public Mensagem() {
        this.timestamp = LocalDateTime.now();
    }
    
    public Mensagem(String autor, String texto) {
        this.autor = autor;
        this.texto = texto;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getTexto() {
        return texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
