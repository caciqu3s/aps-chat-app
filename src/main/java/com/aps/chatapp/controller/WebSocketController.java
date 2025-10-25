package com.aps.chatapp.controller;

import com.aps.chatapp.model.Message;
import com.aps.chatapp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    
    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        message = messageRepository.save(message);
        return message;
    }
}
