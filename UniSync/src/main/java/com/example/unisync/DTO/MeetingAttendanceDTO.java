package com.example.unisync.DTO;

import com.example.unisync.Model.AttendanceStatus;

public class MeetingAttendanceDTO extends BaseDto{
        private Long userId;
        private Long meetingId;
        private AttendanceStatus attendanceStatus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
