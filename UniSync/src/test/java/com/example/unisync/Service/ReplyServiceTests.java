package com.example.unisync.Service;

import com.example.unisync.Model.Reply;
import com.example.unisync.Repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ReplyServiceTest {

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyService replyService;

    public ReplyServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllReplies() {
        List<Reply> mockReplies = Arrays.asList(
                new Reply(1L, "1"),
                new Reply(2L, "2")
        );
        when(replyRepository.findAll()).thenReturn(mockReplies);

        List<Reply> result = replyService.getAll();

        assertEquals(2, result.size());
        assertEquals(mockReplies, result);
    }

    @Test
    void getById_WithValidId_ShouldReturnReply() {
        Reply mockReply = new Reply(1L, "abc");
        when(replyRepository.findById(1L)).thenReturn(Optional.of(mockReply));

        Optional<Reply> result = replyService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(mockReply, result.get());
    }

    @Test
    void getById_WithInvalidId_ShouldReturnEmptyOptional() {
        long invalidReplyId = 123456L;
        when(replyRepository.findById(invalidReplyId)).thenReturn(Optional.empty());

        Optional<Reply> result = replyService.getById(invalidReplyId);

        assertTrue(result.isEmpty());
    }

    @Test
    void delete_ShouldDeleteReply() {
        replyService.delete(1L);

        verify(replyRepository, times(1)).deleteById(1L);
    }
}