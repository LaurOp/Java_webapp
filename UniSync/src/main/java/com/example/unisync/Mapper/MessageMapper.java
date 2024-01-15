package com.example.unisync.Mapper;

import com.example.unisync.DTO.MessageDTO;
import com.example.unisync.Model.Message;
import com.example.unisync.Model.Reply;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {
    public Message map(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        return message;
    }

    public MessageDTO map(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(message.getContent());
        messageDTO.setUserId(message.getUser().getId());
        messageDTO.setcourseId(message.getCourse().getId());
        messageDTO.setLikedByUserIds(message.getLikes().stream().map(like -> like.getUser().getId()).collect(Collectors.toList()));
        return messageDTO;
    }

    public List<MessageDTO> map(List<Message> messages) {
        return messages.stream().map(this::map).collect(Collectors.toList());
    }

    public Message map(Reply reply) {
        Message message = new Message();
        message.setContent(reply.getContent());
        return message;
    }
}
