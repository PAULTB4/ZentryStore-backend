package com.zentry.zentrystore.application.messaging.command;

public class DeleteConversationCommand {
    private final Long conversationId;
    private final Long userId;

    public DeleteConversationCommand(Long conversationId, Long userId) {
        this.conversationId = conversationId;
        this.userId = userId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public Long getUserId() {
        return userId;
    }
}