package com.zentry.zentrystore.application.messaging.query;

public class GetConversationByIdQuery {

    private final Long conversationId;
    private final Long userId;

    public GetConversationByIdQuery(Long conversationId, Long userId) {
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