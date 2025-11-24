package com.zentry.zentrystore.application.messaging.command;

public class MarkMessageAsReadCommand {

    private final Long messageId;
    private final Long userId;

    public MarkMessageAsReadCommand(Long messageId, Long userId) {
        this.messageId = messageId;
        this.userId = userId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Long getUserId() {
        return userId;
    }
}