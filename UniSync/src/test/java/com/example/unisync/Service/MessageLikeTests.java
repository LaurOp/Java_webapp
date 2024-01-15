package com.example.unisync.Service;

import com.example.unisync.Model.Message;
import com.example.unisync.Model.MessageLike;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.MessageLikeRepository;
import com.example.unisync.Repository.MessageRepository;
import com.example.unisync.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MessageLikeTests {
    @Mock
    private MessageLikeRepository messageLikeRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageLikeService messageLikeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void likeMessage() {
        Long userId = 1L;
        Long messageId = 2L;

        User user = new User();
        Message message = new Message();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));
        when(messageLikeRepository.findByUserIdAndMessageId(userId, messageId)).thenReturn(null);

        messageLikeService.likeMessage(userId, messageId);

        verify(userRepository, times(1)).findById(userId);
        verify(messageRepository, times(1)).findById(messageId);
        verify(messageLikeRepository, times(1)).findByUserIdAndMessageId(userId, messageId);
        verify(messageLikeRepository, times(1)).save(any(MessageLike.class));
        verify(userRepository, times(1)).save(user);
        verify(messageRepository, times(1)).save(message);

        assertEquals(1, user.getMessageLikes().size());
        assertEquals(1, message.getLikes().size());
    }

    @Test
    void unlikeMessage() {
        Long userId = 1L;
        Long messageId = 2L;

        MessageLike existingLike = new MessageLike();

        when(messageLikeRepository.findByUserIdAndMessageId(userId, messageId)).thenReturn(existingLike);

        messageLikeService.unlikeMessage(userId, messageId);

        verify(messageLikeRepository, times(1)).findByUserIdAndMessageId(userId, messageId);
        verify(messageLikeRepository, times(1)).delete(existingLike);
    }
}
