package com.zentry.zentrystore.application.notification.query;

public class GetUnreadNotificationsCountQuery {

    private final Long userId;

    public GetUnreadNotificationsCountQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}