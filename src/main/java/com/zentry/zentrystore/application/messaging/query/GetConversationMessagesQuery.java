package com.zentry.zentrystore.application.messaging.query;

public class GetConversationMessagesQuery {

    private final Long conversationId;
    private final Long userId;

    public GetConversationMessagesQuery(Long conversationId, Long userId) {
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