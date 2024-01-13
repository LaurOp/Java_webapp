package com.example.unisync.Controller;

import com.example.unisync.DTO.MeetingDTO;
import com.example.unisync.Exception.NotFoundException;
import com.example.unisync.Exception.UnauthorizedException;
import com.example.unisync.Mapper.MeetingMapper;
import com.example.unisync.Model.Meeting;
import com.example.unisync.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController extends BaseController{

    private final MeetingService meetingService;
    private final MeetingMapper meetingMapper;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
        this.meetingMapper = new MeetingMapper();
    }

    @PostMapping("/create/{teacherUserId}/{courseId}")
    public ResponseEntity<MeetingDTO> createMeeting(
            @PathVariable Long teacherUserId,
            @PathVariable Long courseId,
            @RequestBody MeetingDTO meetingDTO) {

        try {
            MeetingDTO createdMeeting = meetingMapper.map(meetingService.createMeetingWithInvitations(
                    teacherUserId,
                    courseId,
                    meetingMapper.map(meetingDTO),
                    meetingDTO.getInvitedUserIds()));
            return new ResponseEntity<>(createdMeeting, HttpStatus.CREATED);
        } catch (NotFoundException | UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byCourse/{courseId}")
    public ResponseEntity<List<MeetingDTO>> getMeetingsByCourseId(@PathVariable Long courseId) {
        List<Meeting> meetings = meetingService.getMeetingsByCourseId(courseId);
        List<MeetingDTO> meetingDTOs = meetingMapper.map(meetings);
        return new ResponseEntity<>(meetingDTOs, HttpStatus.OK);
    }
}
