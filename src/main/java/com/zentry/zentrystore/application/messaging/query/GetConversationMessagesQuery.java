package com.zentry.zentrystore.application.messaging.query;

import org.springframework.data.domain.Pageable;

public class GetConversationMessagesQuery {

    private final Long conversationId;
    private final Long userId;

    public GetConversationMessagesQuery(Long conversationId, Long userId, Pageable pageable) {
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