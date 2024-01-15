package com.example.unisync.Mapper;

import com.example.unisync.DTO.MeetingDTO;
import com.example.unisync.Model.Course;
import com.example.unisync.Model.Meeting;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingMapper {

    public Meeting map (MeetingDTO meetingDTO){
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDTO.getTitle());
        meeting.setStartTime(meetingDTO.getStartTime());
        return meeting;
    }

    public MeetingDTO map (Meeting meeting){
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setId(meeting.getId());
        meetingDTO.setTitle(meeting.getTitle());
        meetingDTO.setStartTime(meeting.getStartTime());
        meetingDTO.setInvitedUserIds(meeting.getMeetingAttendances().stream().map(invitation -> invitation.getUser().getId()).collect(Collectors.toList()));
        return meetingDTO;
    }

    public List<MeetingDTO> map (List<Meeting> meetings){
        return meetings.stream().map(this::map).collect(Collectors.toList());
    }
}
