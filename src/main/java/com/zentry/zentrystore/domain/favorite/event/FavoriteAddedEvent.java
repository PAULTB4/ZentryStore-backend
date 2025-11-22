package com.zentry.zentrystore.domain.favorite.event;

import java.time.LocalDateTime;

public class FavoriteAddedEvent {

    private final Long favoriteId;
    private final Long userId;
    private final Long publicationId;
    private final LocalDateTime occurredOn;

    public FavoriteAddedEvent(Long favoriteId, Long userId, Long publicationId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.publicationId = publicationId;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "FavoriteAddedEvent{" +
                "favoriteId=" + favoriteId +
                ", userId=" + userId +
                ", publicationId=" + publicationId +
                ", occurredOn=" + occurredOn +
                '}';
    }
}