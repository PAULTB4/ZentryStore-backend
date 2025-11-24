package com.zentry.zentrystore.application.messaging.query;

public class GetUserConversationsQuery {

    private final Long userId;

    public GetUserConversationsQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}