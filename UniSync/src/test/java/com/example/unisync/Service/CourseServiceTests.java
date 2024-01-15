package com.example.unisync.Service;

import com.example.unisync.Model.Course;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses() {
        List<Course> courses = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAll();

        assertEquals(courses, result);
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void getCourseById() {
        Course course = new Course();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getById(1L);

        assertEquals(Optional.of(course), result);
        verify(courseRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteCourseById() {
        courseService.delete(1L);

        verify(courseRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void saveCourse() {
        Course course = new Course();
        courseService.save(course);

        verify(courseRepository, times(1)).save(course);
    }

}
