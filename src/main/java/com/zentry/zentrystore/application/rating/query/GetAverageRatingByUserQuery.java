package com.zentry.zentrystore.application.rating.query;

public class GetAverageRatingByUserQuery {

    private final Long userId;

    public GetAverageRatingByUserQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}