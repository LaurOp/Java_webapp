package com.example.unisync.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reply extends Message {
    @ManyToOne
    @JoinColumn(name = "parent_message_id")
    private Message parentMessage;

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }
}
