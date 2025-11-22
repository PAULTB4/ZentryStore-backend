package com.zentry.zentrystore.application.favorite.query;

public class GetUserFavoritesQuery {

    private final Long userId;

    public GetUserFavoritesQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}