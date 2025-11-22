package com.zentry.zentrystore.application.notification.query;

public class GetUnreadNotificationsQuery {

    private final Long userId;

    public GetUnreadNotificationsQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}