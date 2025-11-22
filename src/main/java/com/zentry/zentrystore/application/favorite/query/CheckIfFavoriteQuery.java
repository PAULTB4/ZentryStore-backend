package com.zentry.zentrystore.application.favorite.query;

public class CheckIfFavoriteQuery {

    private final Long userId;
    private final Long publicationId;

    public CheckIfFavoriteQuery(Long userId, Long publicationId) {
        this.userId = userId;
        this.publicationId = publicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}