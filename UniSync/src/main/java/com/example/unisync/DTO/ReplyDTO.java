package com.example.unisync.DTO;

public class ReplyDTO extends MessageDTO{
    public Long parentMessageId;

    public Long getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Long parentMessageId) {
        this.parentMessageId = parentMessageId;
    }
}
