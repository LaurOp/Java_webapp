package com.example.unisync.Service;

import com.example.unisync.Model.Invitation;
import com.example.unisync.Repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitationService implements BaseService<Invitation>{
    private final InvitationRepository invitationRepository;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository){
        this.invitationRepository = invitationRepository;
    }

    @Override
    public List<Invitation> getAll() {
        return invitationRepository.findAll();
    }

    @Override
    public Optional<Invitation> getById(Long id) {
        return invitationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
          invitationRepository.deleteById(id);
    }
}
