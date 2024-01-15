package com.example.unisync.Service;

import com.example.unisync.Config.AttendanceStatus;
import com.example.unisync.Model.MeetingAttendance;
import com.example.unisync.Repository.MeetingAttendanceRepository;
import com.example.unisync.Repository.MeetingRepository;
import com.example.unisync.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingAttendanceService implements BaseService<MeetingAttendance>{

    private final MeetingAttendanceRepository meetingAttendanceRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Autowired
    public MeetingAttendanceService(MeetingAttendanceRepository meetingAttendanceRepository, MeetingRepository meetingRepository, UserRepository userRepository) {
        this.meetingAttendanceRepository = meetingAttendanceRepository;
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<MeetingAttendance> getAll() {
        return meetingAttendanceRepository.findAll();
    }

    @Override
    public Optional<MeetingAttendance> getById(Long id) {
        return meetingAttendanceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        meetingAttendanceRepository.delete(getById(id).get());
    }

    public void markAttendance(Long userId, Long meetingId, AttendanceStatus attendanceStatus) {
        Optional<MeetingAttendance> existingAttendance = meetingAttendanceRepository.findByUserIdAndMeetingId(userId, meetingId);

        existingAttendance.ifPresentOrElse(
                attendance -> updateAttendanceStatus(attendance, attendanceStatus),
                () -> createNewAttendance(userId, meetingId, attendanceStatus)
        );
    }

    private void createNewAttendance(Long userId, Long meetingId, AttendanceStatus attendanceStatus) {
        MeetingAttendance meetingAttendance = new MeetingAttendance();

        var user = userRepository.findById(userId).get();
        var meeting = meetingRepository.findById(meetingId).get();

        meetingAttendance.setUser(user);
        meetingAttendance.setMeeting(meeting);
        meetingAttendance.setAttendanceStatus(attendanceStatus);

        meetingAttendanceRepository.save(meetingAttendance);

        user.getMeetingAttendances().add(meetingAttendance);
        meeting.getMeetingAttendances().add(meetingAttendance);

        userRepository.save(user);
        meetingRepository.save(meeting);
    }

    private void updateAttendanceStatus(MeetingAttendance existingAttendance, AttendanceStatus newStatus) {
        if (existingAttendance.getAttendanceStatus() != newStatus) {
            existingAttendance.setAttendanceStatus(newStatus);
            meetingAttendanceRepository.save(existingAttendance);
        }
    }

    public MeetingAttendance createDefaultAttendance(Long userId, Long meetingId){
        MeetingAttendance meetingAttendance = new MeetingAttendance();

        var user = userRepository.findById(userId).get();
        var meeting = meetingRepository.findById(meetingId).get();

        meetingAttendance.setUser(user);
        meetingAttendance.setMeeting(meeting);
        meetingAttendance.setAttendanceStatus(AttendanceStatus.WILL_NOT_ATTEND);

        meetingAttendanceRepository.save(meetingAttendance);

        if (user.getMeetingAttendances() == null) {
            user.setMeetingAttendances(new ArrayList<>());
        }
        if (meeting.getMeetingAttendances() == null) {
            meeting.setMeetingAttendances(new ArrayList<>());
        }

        user.getMeetingAttendances().add(meetingAttendance);
        meeting.getMeetingAttendances().add(meetingAttendance);

        userRepository.save(user);
        meetingRepository.save(meeting);

        return meetingAttendance;
    }

    public List<MeetingAttendance> getFutureAttendancesForUser(Long userId) {
        var currentDateTime = LocalDateTime.now();
        var ma = meetingAttendanceRepository.findByUserIdAndMeetingStartTimeAfter(userId, currentDateTime);
        return ma;
    }
}
