package com.example.unisync.Model;

import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public BaseModel(Long id) {
        this.id = id;
    }

    public BaseModel() {
    }
}