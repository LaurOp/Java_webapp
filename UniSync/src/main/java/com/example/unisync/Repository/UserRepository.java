package com.example.unisync.Repository;

import com.example.unisync.Model.User;
import com.example.unisync.Repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
   @Query("SELECT u FROM User u WHERE u.id = :userId")
   Optional<User> findById(@Param("userId") Long userId);

   @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.isUniversity = true")
    List<User> findUniversities();

    @Query("SELECT u FROM User u WHERE u.isTeacher = true")
    List<User> findTeachers();

    @Query("SELECT u FROM User u WHERE u.isUniversity = false AND u.isTeacher = false")
    List<User> findUsers();

    @Query("SELECT u FROM User u WHERE u.createdCourses IS NOT EMPTY")
    List<User> findUsersWithCreatedCourses();

    @Query("SELECT u FROM User u WHERE u.administeredCourses IS NOT EMPTY")
    List<User> findUsersWithAdministeredCourses();

    @Query("SELECT u FROM User u JOIN FETCH u.messages WHERE u.id = :userId")
    Optional<User> findUserWithMessages(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.id IN :userIds")
    List<User> findUsersByIds(List<Long> userIds);

    @Query("SELECT u FROM User u JOIN FETCH u.enrolledCourses c WHERE c.id = :courseId")
    List<User> findAllUsersInACourse(@Param("courseId") Long courseId);
}
