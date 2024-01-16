package com.example.unisync.Service;

import com.example.unisync.Model.Invitation;
import com.example.unisync.Repository.InvitationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvitationServiceTests {

    @Mock
    private InvitationRepository invitationRepository;

    @InjectMocks
    private InvitationService invitationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllInvitations() {
        Invitation invitation1 = new Invitation();
        Invitation invitation2 = new Invitation();
        List<Invitation> mockInvitations = Arrays.asList(invitation1, invitation2);

        when(invitationRepository.findAll()).thenReturn(mockInvitations);

        List<Invitation> result = invitationService.getAll();

        assertEquals(mockInvitations, result);
        verify(invitationRepository, times(1)).findAll();
    }

    @Test
    void getInvitationById() {
        Invitation mockInvitation = new Invitation();

        when(invitationRepository.findById(anyLong())).thenReturn(Optional.of(mockInvitation));

        Optional<Invitation> result = invitationService.getById(1L);

        assertEquals(Optional.of(mockInvitation), result);
        verify(invitationRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteInvitation() {
        invitationService.delete(1L);

        verify(invitationRepository, times(1)).deleteById(1L);
    }
}
