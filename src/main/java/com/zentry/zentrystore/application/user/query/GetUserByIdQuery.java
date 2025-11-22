package com.zentry.zentrystore.application.user.query;

public class GetUserByIdQuery {

    private final Long userId;

    public GetUserByIdQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}