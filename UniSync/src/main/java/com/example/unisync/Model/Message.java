package com.example.unisync.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Message extends BaseModel {

    public Message() {
    }

    public Message(Long id){
        super(id);
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "parentMessage", cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<MessageLike> likes = new ArrayList<>();

    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MessageLike> getLikes() {
        return likes;
    }

    public void setLikes(List<MessageLike> likes) {
        this.likes = likes;
    }
}
