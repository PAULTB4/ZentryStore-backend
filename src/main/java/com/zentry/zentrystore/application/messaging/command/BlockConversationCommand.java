package com.zentry.zentrystore.application.messaging.command;

public class BlockConversationCommand {

    private final Long conversationId;
    private final Long userId;

    public BlockConversationCommand(Long conversationId, Long userId) {
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