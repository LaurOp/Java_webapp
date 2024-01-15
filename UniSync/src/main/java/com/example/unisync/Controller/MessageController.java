package com.example.unisync.Controller;

import com.example.unisync.DTO.MessageDTO;
import com.example.unisync.DTO.ReplyDTO;
import com.example.unisync.Exception.UnauthorizedException;
import com.example.unisync.Mapper.MessageMapper;
import com.example.unisync.Model.Message;
import com.example.unisync.Service.MessageLikeService;
import com.example.unisync.Service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.unisync.Constants.*;

@RestController
@RequestMapping("/api/message")
public class MessageController extends BaseController{
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final MessageLikeService messageLikeService;

    @Autowired
    public MessageController(MessageService messageService, MessageMapper messageMapper, MessageLikeService messageLikeService) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.messageLikeService = messageLikeService;
    }

    @PostMapping("/post")
    public ResponseEntity<MessageDTO> postMessage(@RequestBody MessageDTO messageDTO) {
        try {
            Message postedMessage = messageService.postMessage(messageDTO);
            return new ResponseEntity<>(messageMapper.map(postedMessage), HttpStatus.CREATED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{messageId}/like/{userId}")
    public ResponseEntity<String> likeMessage(@PathVariable Long messageId, @PathVariable Long userId) {
        try {
            messageLikeService.likeMessage(userId, messageId);
            return new ResponseEntity<>(MESSAGE_LIKED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_LIKING_MESSAGE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{messageId}/unlike/{userId}")
    public ResponseEntity<String> unlikeMessage(@PathVariable Long messageId, @PathVariable Long userId) {
        try {
            messageLikeService.unlikeMessage(userId, messageId);
            return new ResponseEntity<>(MESSAGE_UNLIKED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_UNLIKING_MESSAGE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reply")
    public ResponseEntity<String> createReply(@RequestBody ReplyDTO replyDTO) {
        try {
            messageService.postReply(replyDTO);
            return new ResponseEntity<>(REPLY_CREATED_SUCCESSFULLY, HttpStatus.CREATED);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_CREATING_REPLY + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
