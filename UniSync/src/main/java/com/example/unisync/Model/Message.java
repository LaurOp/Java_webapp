package com.example.unisync.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Message extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<Reply> replies;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
