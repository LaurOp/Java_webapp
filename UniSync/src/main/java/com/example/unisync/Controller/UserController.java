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

import static com.example.unisync.Constants.*;

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
                return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
            }

            userService.createCourseIfUniversity(user.get(), course);
            return new ResponseEntity<>(COURSE_CREATED_SUCCESSFULLY, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_CREATING_COURSE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
                return new ResponseEntity<>(USER_OR_COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }

            User user = userOptional.get();
            Course course = courseOptional.get();

            if (user.isTeacher()){
                course.setAdmin(user);
                courseService.save(course);
                return new ResponseEntity<>(USER_ASSIGNED_AS_COURSE_ADMIN_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ADMIN_MUST_BE_A_TEACHER, HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_ASSIGNING_COURSE_ADMIN + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
                return new ResponseEntity<>(USER_OR_COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }

            User user = userOptional.get();
            Course course = courseOptional.get();

            if (!user.isUniversity()) {
                course.getStudents().add(user);
                courseService.save(course);
                return new ResponseEntity<>(TO_COURSE_SUCCESSFULLY, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(DOES_NOT_HAVE_PERMISSION, HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_ADDING_STUDENT_TO_COURSE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
                return new ResponseEntity<>(USER_OR_COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }

            User student = userOptional.get();
            Course course = courseOptional.get();

            if (student.getEnrolledCourses().contains(course)) {
                return new ResponseEntity<>(ALREADY_ENROLLED_IN_THE_COURSE, HttpStatus.BAD_REQUEST);
            }

            userService.enrollStudentInCourse(student, course);

            return new ResponseEntity<>(ENROLLED_IN_THE_COURSE_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_ENROLLING_IN_THE_COURSE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
