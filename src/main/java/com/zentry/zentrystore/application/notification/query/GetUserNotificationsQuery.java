package com.zentry.zentrystore.application.notification.query;

public class GetUserNotificationsQuery {

    private final Long userId;
    private final Integer limit;

    public GetUserNotificationsQuery(Long userId, Integer limit) {
        this.userId = userId;
        this.limit = limit;
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getLimit() {
        return limit;
    }
}