package com.example.unisync.Service;

import com.example.unisync.DTO.MessageDTO;
import com.example.unisync.DTO.ReplyDTO;
import com.example.unisync.Exception.UnauthorizedException;
import com.example.unisync.Model.Course;
import com.example.unisync.Model.Message;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.CourseRepository;
import com.example.unisync.Repository.MessageRepository;
import com.example.unisync.Repository.ReplyRepository;
import com.example.unisync.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static com.example.unisync.Config.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MessageServiceTests {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MessageLikeService messageLikeService;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void postMessage_UnauthorizedException() {
        User user = new User(1L);
        user.setEnrolledCourses(Collections.emptyList());

        Course course = new Course(1L);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUserId(1L);
        messageDTO.setcourseId(1L);
        messageDTO.setContent(TEST_MESSAGE_CONTENT);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> messageService.postMessage(messageDTO));

        assertEquals(PART_OF_THE_COURSE_CANNOT_POST_MESSAGE, exception.getMessage());
    }

    @Test
    void postReply_UnauthorizedException() {
        User user = new User(1L);
        user.setEnrolledCourses(Collections.emptyList());

        Course course = new Course(1L);

        Message parentMessage = new Message(1L);
        parentMessage.setUser(user);
        parentMessage.setCourse(course);

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setUserId(1L);
        replyDTO.setParentMessageId(1L);
        replyDTO.setContent(TEST_REPLY_CONTENT);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(messageRepository.findById(1L)).thenReturn(Optional.of(parentMessage));

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> messageService.postReply(replyDTO));

        assertEquals(PART_OF_THE_COURSE_CANNOT_POST_REPLY, exception.getMessage());
    }
}
