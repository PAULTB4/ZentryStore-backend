package com.zentry.zentrystore.domain.publication.event;

import java.time.LocalDateTime;

public class PublicationDeletedEvent {

    private final Long publicationId;
    private final Long userId;
    private final String title;
    private final String reason;
    private final LocalDateTime occurredOn;

    public PublicationDeletedEvent(Long publicationId, Long userId, String title) {
        this(publicationId, userId, title, null);
    }

    public PublicationDeletedEvent(Long publicationId, Long userId, String title, String reason) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.title = title;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "PublicationDeletedEvent{" +
                "publicationId=" + publicationId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", reason='" + reason + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}