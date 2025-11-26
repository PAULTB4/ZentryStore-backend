package com.zentry.zentrystore.application.messaging.command;

public class MarkConversationAsArchivedCommand {
    private final Long conversationId;
    private final Long userId;
    private final boolean archived;

    public MarkConversationAsArchivedCommand(Long conversationId, Long userId, boolean archived) {
        this.conversationId = conversationId;
        this.userId = userId;
        this.archived = archived;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isArchived() {
        return archived;
    }
}