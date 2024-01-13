package com.example.unisync.Repository;

import com.example.unisync.Model.Course;
import com.example.unisync.Model.User;
import com.example.unisync.Repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends BaseRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.id = :courseId")
    Optional<Course> findById(@Param("courseId") Long courseId);

    List<Course> findByCreatedBy(User createdBy);

    List<Course> findByAdmin(User admin);

    @Query("SELECT c FROM Course c WHERE c.meetings IS NOT EMPTY")
    List<Course> findCoursesWithMeetings();

    @Query("SELECT c FROM Course c JOIN FETCH c.messages WHERE c.id = :courseId")
    Course findCourseWithMessages(@Param("courseId") Long courseId);

    @Query("SELECT c.students FROM Course c WHERE c.id = :courseId")
    List<User> findUsersByCourseId(Long courseId);
}
