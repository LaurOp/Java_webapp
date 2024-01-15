package com.example.unisync.Repository;

import com.example.unisync.Model.MessageLike;
import org.springframework.data.jpa.repository.Query;

public interface MessageLikeRepository extends BaseRepository<MessageLike, Long>{

    @Query("SELECT ml FROM MessageLike ml WHERE ml.user.id = ?1 AND ml.message.id = ?2")
    MessageLike findByUserIdAndMessageId(Long userId, Long messageId);
}
