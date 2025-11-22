package com.zentry.zentrystore.domain.publication.event;

import java.time.LocalDateTime;

public class PublicationCreatedEvent {

    private final Long publicationId;
    private final Long userId;
    private final String title;
    private final Long categoryId;
    private final LocalDateTime occurredOn;

    public PublicationCreatedEvent(Long publicationId, Long userId, String title, Long categoryId) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.title = title;
        this.categoryId = categoryId;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "PublicationCreatedEvent{" +
                "publicationId=" + publicationId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", categoryId=" + categoryId +
                ", occurredOn=" + occurredOn +
                '}';
    }
}