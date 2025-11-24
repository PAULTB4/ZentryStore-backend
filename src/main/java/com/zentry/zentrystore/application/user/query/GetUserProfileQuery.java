package com.zentry.zentrystore.application.user.query;

public class GetUserProfileQuery {
    private final Long userId;

    public GetUserProfileQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}