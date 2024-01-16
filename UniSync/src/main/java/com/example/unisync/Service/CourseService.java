package com.example.unisync.Service;

import com.example.unisync.Model.Course;
import com.example.unisync.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements BaseService<Course>{

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

}
