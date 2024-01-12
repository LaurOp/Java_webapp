package com.example.unisync.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Invitation extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

}
