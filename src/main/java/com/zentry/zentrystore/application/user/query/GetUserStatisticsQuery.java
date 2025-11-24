package com.zentry.zentrystore.application.user.query;

public class GetUserStatisticsQuery {

    private final Long userId;

    public GetUserStatisticsQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}