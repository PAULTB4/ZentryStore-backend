package com.zentry.zentrystore.application.publication.command;

public class PublishPublicationCommand {

    private final Long publicationId;
    private final Long userId;

    public PublishPublicationCommand(Long publicationId, Long userId) {
        this.publicationId = publicationId;
        this.userId = userId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Long getUserId() {
        return userId;
    }
}