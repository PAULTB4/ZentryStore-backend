package com.zentry.zentrystore.application.publication.command;

public class ChangePublicationStatusCommand {

    private final Long publicationId;
    private final Long userId;
    private final String newStatus; // ACTIVE, PAUSED, INACTIVE, SOLD

    public ChangePublicationStatusCommand(Long publicationId,Long userId, String newStatus) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.newStatus = newStatus;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNewStatus() {
        return newStatus;
    }
}