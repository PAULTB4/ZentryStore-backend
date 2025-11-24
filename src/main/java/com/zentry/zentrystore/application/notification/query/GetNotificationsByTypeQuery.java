package com.zentry.zentrystore.application.notification.query;

public class GetNotificationsByTypeQuery {

    private final Long userId;
    private final String type;

    public GetNotificationsByTypeQuery(Long userId, String type) {
        this.userId = userId;
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }
}