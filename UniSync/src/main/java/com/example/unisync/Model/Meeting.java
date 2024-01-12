package com.example.unisync.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Meeting extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Invitation> invitations;

}