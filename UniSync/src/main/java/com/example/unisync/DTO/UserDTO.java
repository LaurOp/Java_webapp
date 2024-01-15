package com.example.unisync.DTO;

import com.example.unisync.Model.Course;
import com.example.unisync.Model.Meeting;
import com.example.unisync.Model.Message;
import com.example.unisync.Model.Reply;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;

public class UserDTO extends BaseDto{

    private boolean isUniversity;
    private boolean isTeacher;
    private String username;
    private String password;
    private String email;
    private List<Long> enrolledCourseIds;

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

    public List<Long> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public void setEnrolledCourseIds(List<Long> enrolledCourseIds) {
        this.enrolledCourseIds = enrolledCourseIds;
    }
}
