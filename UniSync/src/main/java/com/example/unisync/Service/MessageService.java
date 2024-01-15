package com.example.unisync.Service;

import com.example.unisync.DTO.MessageDTO;
import com.example.unisync.DTO.ReplyDTO;
import com.example.unisync.Exception.UnauthorizedException;
import com.example.unisync.Model.Course;
import com.example.unisync.Model.Message;
import com.example.unisync.Model.Reply;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.CourseRepository;
import com.example.unisync.Repository.MessageRepository;
import com.example.unisync.Repository.ReplyRepository;
import com.example.unisync.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements BaseService<Message>{
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ReplyRepository replyRepository;
    private final MessageLikeService messageLikeService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository, CourseRepository courseRepository, MessageLikeService messageLikeService, ReplyRepository replyRepository){
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.messageLikeService = messageLikeService;
        this.replyRepository = replyRepository;
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

    public Message postMessage(MessageDTO messageDTO) {
        User user = userRepository.findById(messageDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Course course = courseRepository.findById(messageDTO.getcourseId()).orElseThrow(() -> new EntityNotFoundException("Channel not found"));

        if (!user.getEnrolledCourses().contains(course)) {
            throw new UnauthorizedException("User is not part of the course. Cannot post message.");
        }

        Message message = new Message();
        message.setUser(user);
        message.setCourse(course);
        message.setContent(messageDTO.getContent());

        var savedMessage = messageRepository.save(message);

        if (messageDTO.getLikedByUserIds() != null) {
            for (Long userId : messageDTO.getLikedByUserIds()) {
                messageLikeService.likeMessage(userId, savedMessage.getId());
            }
        }

        return savedMessage;
    }

    public Reply postReply(ReplyDTO replyDTO) {
        User user = userRepository.findById(replyDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Message parentMessage = messageRepository.findById(replyDTO.getParentMessageId()).orElseThrow(() -> new EntityNotFoundException("Parent message not found"));

        if (!user.getEnrolledCourses().contains(parentMessage.getCourse())) {
            throw new UnauthorizedException("User is not part of the course. Cannot post reply.");
        }

        Reply reply = new Reply();
        reply.setUser(user);
        reply.setCourse(parentMessage.getCourse());
        reply.setParentMessage(parentMessage);
        reply.setContent(replyDTO.getContent());

        var saved = messageRepository.save(reply);

        user.getReplies().add(saved);
        parentMessage.getReplies().add(saved);

        userRepository.save(user);
        messageRepository.save(parentMessage);

        return saved;
    }
}
