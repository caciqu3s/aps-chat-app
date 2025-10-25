package com.aps.chatapp.controller;

import com.aps.chatapp.model.Mensagem;
import com.aps.chatapp.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    
    @Autowired
    private MensagemRepository mensagemRepository;
    
    @MessageMapping("/relatorio")
    @SendTo("/topic/dashboard")
    public Mensagem handleRelatorio(Mensagem mensagem) {
        mensagem = mensagemRepository.save(mensagem);
        return mensagem;
    }
}
