package com.example.unisync.Repository;

import com.example.unisync.Model.MessageLike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageLikeRepository extends BaseRepository<MessageLike, Long>{

    @Query("SELECT ml FROM MessageLike ml WHERE ml.user.id = :userId AND ml.message.id = :messageId")
    MessageLike findByUserIdAndMessageId(@Param("userId") Long userId, @Param("messageId") Long messageId);
}
