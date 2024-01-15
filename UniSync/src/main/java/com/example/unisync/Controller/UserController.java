package com.example.unisync.Controller;

import com.example.unisync.DTO.UserDTO;
import com.example.unisync.Mapper.UserMapper;
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
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, CourseService courseService, UserMapper userMapper) {
        this.userService = userService;
        this.courseService = courseService;
        this.userMapper = userMapper;
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
    public ResponseEntity<UserDTO> createUserEndpoint(@RequestBody UserDTO user) {
        try {
            User createdUser = userService.createUser(userMapper.map(user));
            return new ResponseEntity<>(userMapper.map(createdUser), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}/assign-course-admin/{courseId}")
    public ResponseEntity<String> assignCourseAdmin(
            @PathVariable Long userId,
            @PathVariable Long courseId) {

        try {
            Optional<User> userOptional = userService.getById(userId);
            Optional<Course> courseOptional = courseService.getById(courseId);

            if (userOptional.isEmpty() || courseOptional.isEmpty()) {
                return new ResponseEntity<>("User or Course not found", HttpStatus.NOT_FOUND);
            }

            User user = userOptional.get();
            Course course = courseOptional.get();

            if (user.isTeacher()){
                course.setAdmin(user);
                courseService.save(course);
                return new ResponseEntity<>("User assigned as course admin successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User does not have permission to assign course admin; must be a teacher.", HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Error assigning course admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/add-student-to-course/{courseId}")
    public ResponseEntity<String> addStudentToCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId) {

        try {
            Optional<User> userOptional = userService.getById(userId);
            Optional<Course> courseOptional = courseService.getById(courseId);

            if (userOptional.isEmpty() || courseOptional.isEmpty()) {
                return new ResponseEntity<>("User or Course not found", HttpStatus.NOT_FOUND);
            }

            User user = userOptional.get();
            Course course = courseOptional.get();

            if (!user.isUniversity()) {
                course.getStudents().add(user);
                courseService.save(course);
                return new ResponseEntity<>("Student added to course successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User is not a student or does not have permission", HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Error adding student to course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/enroll-in-course/{courseId}")
    public ResponseEntity<String> enrollInCourse(
            @PathVariable Long userId,
            @PathVariable Long courseId) {

        try {
            Optional<User> userOptional = userService.getById(userId);
            Optional<Course> courseOptional = courseService.getById(courseId);

            if (userOptional.isEmpty() || courseOptional.isEmpty()) {
                return new ResponseEntity<>("User or Course not found", HttpStatus.NOT_FOUND);
            }

            User student = userOptional.get();
            Course course = courseOptional.get();

            if (student.getEnrolledCourses().contains(course)) {
                return new ResponseEntity<>("User is already enrolled in the course", HttpStatus.BAD_REQUEST);
            }

            userService.enrollStudentInCourse(student, course);

            return new ResponseEntity<>("Enrolled in the course successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error enrolling in the course: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
