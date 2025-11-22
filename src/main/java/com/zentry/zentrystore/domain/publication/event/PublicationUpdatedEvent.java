package com.zentry.zentrystore.domain.publication.event;

import java.time.LocalDateTime;

public class PublicationUpdatedEvent {

    private final Long publicationId;
    private final Long userId;
    private final String title;
    private final String updatedField;
    private final LocalDateTime occurredOn;

    public PublicationUpdatedEvent(Long publicationId, Long userId, String title) {
        this(publicationId, userId, title, null);
    }

    public PublicationUpdatedEvent(Long publicationId, Long userId, String title, String updatedField) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.title = title;
        this.updatedField = updatedField;
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

    public String getUpdatedField() {
        return updatedField;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "PublicationUpdatedEvent{" +
                "publicationId=" + publicationId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", updatedField='" + updatedField + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}