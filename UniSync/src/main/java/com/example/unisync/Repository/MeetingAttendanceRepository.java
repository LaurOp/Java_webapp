package com.example.unisync.Repository;

import com.example.unisync.Model.MeetingAttendance;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingAttendanceRepository extends BaseRepository<MeetingAttendance, Long>{

    @Query("SELECT ma FROM MeetingAttendance ma WHERE ma.user.id = ?1 AND ma.meeting.id = ?2")
    public Optional<MeetingAttendance> findByUserIdAndMeetingId(Long userId, Long meetingId);

    @Query("SELECT ma FROM MeetingAttendance ma WHERE ma.user.id = ?1 AND ma.meeting.startTime > ?2")
    List<MeetingAttendance> findByUserIdAndMeetingStartTimeAfter(Long userId, LocalDateTime startTime);
}
