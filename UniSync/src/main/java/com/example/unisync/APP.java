package com.example.unisync;

import com.example.unisync.Exception.LocalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(LocalExceptionHandler.class)
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class, args);
    }
}