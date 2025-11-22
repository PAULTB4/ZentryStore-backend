package com.zentry.zentrystore.application.rating.query;

public class GetRatingsByUserQuery {

    private final Long userId;

    public GetRatingsByUserQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}