package com.zentry.zentrystore.application.messaging.command;

public class DeleteMessageCommand {
    private final Long messageId;
    private final Long userId;

    public DeleteMessageCommand(Long messageId, Long userId) {
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