package com.zentry.zentrystore.domain.publication.event;

import com.zentry.zentrystore.domain.publication.model.PublicationStatus;

import java.time.LocalDateTime;

public class PublicationStatusChangedEvent {

    private final Long publicationId;
    private final Long userId;
    private final PublicationStatus oldStatus;
    private final PublicationStatus newStatus;
    private final LocalDateTime occurredOn;

    public PublicationStatusChangedEvent(Long publicationId, Long userId,
                                         PublicationStatus oldStatus, PublicationStatus newStatus) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.occurredOn = LocalDateTime.now();
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public PublicationStatus getOldStatus() {
        return oldStatus;
    }

    public PublicationStatus getNewStatus() {
        return newStatus;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String toString() {
        return "PublicationStatusChangedEvent{" +
                "publicationId=" + publicationId +
                ", userId=" + userId +
                ", oldStatus=" + oldStatus +
                ", newStatus=" + newStatus +
                ", occurredOn=" + occurredOn +
                '}';
    }
}