package com.example.unisync.Service;

import com.example.unisync.Model.Course;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.CourseRepository;
import com.example.unisync.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements BaseService<User> {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public void createCourseIfUniversity(User user) {
        if (user.isUniversity()) {
            Course newCourse = new Course();
            newCourse.setCreatedBy(user);
            newCourse.setAdmin(user);

            courseRepository.save(newCourse);
            userRepository.save(user);
        }
    }

    public void createCourseIfUniversity(User user, Course newCourse) {
        if (user.isUniversity()) {
            newCourse.setCreatedBy(user);
            newCourse.setAdmin(user);

            courseRepository.save(newCourse);
            userRepository.save(user);
        }
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
