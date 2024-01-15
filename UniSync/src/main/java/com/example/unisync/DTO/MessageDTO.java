package com.example.unisync.DTO;

import java.util.List;

public class MessageDTO extends BaseDto{
        private Long userId;
        private Long courseId;
        private String content;
        private List<Long> likedByUserIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getcourseId() {
        return courseId;
    }

    public void setcourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Long> getLikedByUserIds() {
        return likedByUserIds;
    }

    public void setLikedByUserIds(List<Long> likedByUserIds) {
        this.likedByUserIds = likedByUserIds;
    }
}
