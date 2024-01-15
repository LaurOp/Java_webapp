package com.example.unisync.Mapper;

import com.example.unisync.DTO.MeetingAttendanceDTO;
import com.example.unisync.Model.MeetingAttendance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingAttendanceMapper {
    public MeetingAttendanceDTO map(MeetingAttendance meetingAttendance) {
        MeetingAttendanceDTO meetingAttendanceDTO = new MeetingAttendanceDTO();
        meetingAttendanceDTO.setMeetingId(meetingAttendance.getMeeting().getId());
        meetingAttendanceDTO.setUserId(meetingAttendance.getUser().getId());
        meetingAttendanceDTO.setAttendanceStatus(meetingAttendance.getAttendanceStatus());
        return meetingAttendanceDTO;
    }

    public MeetingAttendance map(MeetingAttendanceDTO meetingAttendanceDTO) {
        MeetingAttendance meetingAttendance = new MeetingAttendance();
        return meetingAttendance;
    }

    public List<MeetingAttendanceDTO> map(List<MeetingAttendance> meetingAttendances) {
        return meetingAttendances.stream().map(this::map).collect(Collectors.toList());
    }
}
