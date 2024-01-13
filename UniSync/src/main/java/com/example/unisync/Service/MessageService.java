package com.example.unisync.Service;

import com.example.unisync.Model.Message;
import com.example.unisync.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements BaseService<Message>{
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> getById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
          messageRepository.deleteById(id);
    }
}
