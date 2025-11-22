package com.zentry.zentrystore.domain.publication.event;

import java.time.LocalDateTime;

public class PublicationViewedEvent {

    private final Long publicationId;
    private final Long userId;
    private final LocalDateTime viewedAt;

    public PublicationViewedEvent(Long publicationId, Long userId) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.viewedAt = LocalDateTime.now();
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }
}