package com.example.unisync.Controller;

import com.example.unisync.Model.Course;
import com.example.unisync.Model.User;
import com.example.unisync.Service.CourseService;
import com.example.unisync.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController{

    private final UserService userService;
    private final CourseService courseService;

    @Autowired
    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @PostMapping("/{userId}/CreateCourse")
    public ResponseEntity<String> createCourseForUserEndpoint(@PathVariable Long userId, @RequestBody Course course) {
        try {
            Optional<User> user = userService.getById(userId);
            if (user.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            userService.createCourseIfUniversity(user.get(), course);
            return new ResponseEntity<>("Course created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUserEndpoint(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
