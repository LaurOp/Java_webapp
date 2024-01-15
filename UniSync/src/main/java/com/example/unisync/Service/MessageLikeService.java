package com.example.unisync.Service;

import com.example.unisync.Model.Message;
import com.example.unisync.Model.MessageLike;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.MessageLikeRepository;
import com.example.unisync.Repository.MessageRepository;
import com.example.unisync.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageLikeService {

    private final MessageLikeRepository messageLikeRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageLikeService(MessageLikeRepository messageLikeRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.messageLikeRepository = messageLikeRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public void likeMessage(Long userId, Long messageId) {
        MessageLike existingLike = messageLikeRepository.findByUserIdAndMessageId(userId, messageId);

        var user = userRepository.findById(userId).orElseThrow();
        var message = messageRepository.findById(messageId).orElseThrow();

        if (existingLike == null) {
            MessageLike newLike = new MessageLike();
            newLike.setUser(user);
            newLike.setMessage(message);
            messageLikeRepository.save(newLike);

            user.getMessageLikes().add(newLike);
            message.getLikes().add(newLike);

            userRepository.save(user);
            messageRepository.save(message);
        }
    }

    public void unlikeMessage(Long userId, Long messageId) {
        MessageLike existingLike = messageLikeRepository.findByUserIdAndMessageId(userId, messageId);

        if (existingLike != null) {
            messageLikeRepository.delete(existingLike);
        }
    }
}
