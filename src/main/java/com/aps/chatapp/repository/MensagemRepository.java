package com.aps.chatapp.repository;

import com.aps.chatapp.model.Mensagem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends MongoRepository<Mensagem, String> {
}
