package com.example.unisync.Service;

import com.example.unisync.Exception.NotFoundException;
import com.example.unisync.Model.Course;
import com.example.unisync.Model.Invitation;
import com.example.unisync.Model.Meeting;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.CourseRepository;
import com.example.unisync.Repository.InvitationRepository;
import com.example.unisync.Repository.MeetingRepository;
import com.example.unisync.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.unisync.Constants.*;

@Service
public class MeetingService implements BaseService<Meeting>{

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final InvitationRepository invitationRepository;
    private final MeetingAttendanceService meetingAttendanceService;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, UserRepository userRepository, CourseRepository courseRepository, InvitationRepository invitationRepository, MeetingAttendanceService meetingAttendanceService){
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.invitationRepository = invitationRepository;
        this.meetingAttendanceService = meetingAttendanceService;
    }

    @Override
    public List<Meeting> getAll() {
        return meetingRepository.findAll();
    }

    @Override
    public Optional<Meeting> getById(Long id) {
        return meetingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        meetingRepository.deleteById(id);
    }

    public Meeting createMeeting(Long teacherUserId, Long courseId, Meeting meeting) throws NotFoundException {
        User teacher = userRepository.findById(teacherUserId).orElseThrow(() -> new NotFoundException(TEACHER_NOT_FOUND));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

        if (!teacher.isTeacher()) {
            throw new NotFoundException(USER_IS_NOT_A_TEACHER);
        }

        meeting.setCourse(course);
        meeting.setCreatedBy(teacher);

        return meetingRepository.save(meeting);
    }

    public List<Meeting> getMeetingsByCourseId(Long courseId) {
        return meetingRepository.findByCourseId(courseId);
    }

    public Meeting createMeetingWithInvitations(Long teacherUserId, Long courseId, Meeting meeting, List<Long> invitedUserIds) {
        meeting.setCreatedBy(userRepository.findById(teacherUserId).orElseThrow(() -> new NotFoundException(TEACHER_NOT_FOUND)));
        meeting.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND)));
        Meeting createdMeeting = meetingRepository.save(meeting);

        createInvitationsForCourseMembers(createdMeeting, courseId, invitedUserIds);
        createDefaultAttendancesForCourseMembers(createdMeeting, courseId, invitedUserIds);

        return createdMeeting;
    }

    private void createInvitationsForCourseMembers(Meeting meeting, Long courseId, List<Long> invitedUserIds) {
        List<User> courseMembers = courseRepository.findUsersByCourseId(courseId);
        List<User> invitedUsers = userRepository.findUsersByIds(invitedUserIds);

        for (User user : courseMembers) {
            Invitation invitation = new Invitation();
            invitation.setMeeting(meeting);
            invitation.setInvitedUser(user);
            invitationRepository.save(invitation);
        }

        for (User user : invitedUsers) {
            if (!courseMembers.contains(user)) {
                Invitation invitation = new Invitation();
                invitation.setMeeting(meeting);
                invitation.setInvitedUser(user);
                invitationRepository.save(invitation);
            }
        }
    }


    private void createDefaultAttendancesForCourseMembers(Meeting meeting, Long courseId, List<Long> invitedUserIds) {
        List<User> courseMembers = userRepository.findAllUsersInACourse(courseId);

        for (User member : courseMembers) {
            if (!invitedUserIds.contains(member.getId())) {
                meetingAttendanceService.createDefaultAttendance(member.getId(), meeting.getId());
            }
        }
    }
}
