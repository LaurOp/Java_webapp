package com.example.unisync.Service;

import com.example.unisync.Model.Reply;
import com.example.unisync.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService implements BaseService<Reply>{
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }

    @Override
    public List<Reply> getAll() {
        return replyRepository.findAll();
    }

    @Override
    public Optional<Reply> getById(Long id) {
        return replyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
          replyRepository.deleteById(id);
    }
}
