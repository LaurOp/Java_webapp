package com.example.unisync.Repository;

import com.example.unisync.Model.MeetingAttendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingAttendanceRepository extends BaseRepository<MeetingAttendance, Long>{

    @Query("SELECT ma FROM MeetingAttendance ma WHERE ma.user.id = :userId AND ma.meeting.id = :meetingId")
    public Optional<MeetingAttendance> findByUserIdAndMeetingId(@Param("userId") Long userId, @Param("meetingId") Long meetingId);

    @Query("SELECT ma FROM MeetingAttendance ma WHERE ma.user.id = :userId AND ma.meeting.startTime > :startTime")
    List<MeetingAttendance> findByUserIdAndMeetingStartTimeAfter(@Param("userId") Long userId, @Param("startTime") LocalDateTime startTime);
}
