package com.zentry.zentrystore.application.messaging.query;

public class GetUnreadMessagesCountQuery {

    private final Long userId;

    public GetUnreadMessagesCountQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}