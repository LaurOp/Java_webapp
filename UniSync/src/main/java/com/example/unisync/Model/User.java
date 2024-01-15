package com.example.unisync.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class User extends BaseModel {

    private boolean isUniversity;
    private boolean isTeacher;
    private String username;
    private String password;
    private String email;

    public User(Long id) {
        super(id);
    }

    public User() {
        super();
    }

    @ManyToMany(mappedBy = "students")
    private List<Course> enrolledCourses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Course> createdCourses;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Course> administeredCourses;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Meeting> createdMeetings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reply> replies;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MeetingAttendance> meetingAttendances;

    public boolean isUniversity() {
        return isUniversity;
    }

    public void setUniversity(boolean university) {
        isUniversity = university;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Course> getCreatedCourses() {
        return createdCourses;
    }

    public void setCreatedCourses(List<Course> createdCourses) {
        this.createdCourses = createdCourses;
    }

    public List<Course> getAdministeredCourses() {
        return administeredCourses;
    }

    public void setAdministeredCourses(List<Course> administeredCourses) {
        this.administeredCourses = administeredCourses;
    }

    public List<Meeting> getCreatedMeetings() {
        return createdMeetings;
    }

    public void setCreatedMeetings(List<Meeting> createdMeetings) {
        this.createdMeetings = createdMeetings;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<MeetingAttendance> getMeetingAttendances() {
        return meetingAttendances;
    }

    public void setMeetingAttendances(List<MeetingAttendance> meetingAttendances) {
        this.meetingAttendances = meetingAttendances;
    }
}
